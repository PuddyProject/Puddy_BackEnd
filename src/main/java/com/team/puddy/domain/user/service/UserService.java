package com.team.puddy.domain.user.service;

import com.team.puddy.domain.answer.domain.Answer;
import com.team.puddy.domain.answer.dto.ResponseAnswerDtoExcludeUser;
import com.team.puddy.domain.answer.repository.AnswerQueryRepository;
import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.image.service.ImageService;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.dto.response.ResponseQuestionExcludeAnswerDto;
import com.team.puddy.domain.question.repository.QuestionRepository;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.dto.request.FindAccountDto;
import com.team.puddy.domain.user.dto.request.LoginUserRequest;
import com.team.puddy.domain.user.dto.request.RegisterUserRequest;
import com.team.puddy.domain.user.dto.response.ResponsePostDto;
import com.team.puddy.domain.user.dto.response.ResponseUserInfoDto;
import com.team.puddy.domain.user.dto.response.TokenReissueDto;
import com.team.puddy.domain.user.repository.UserQueryRepository;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.config.security.jwt.JwtVerifier;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.mapper.AnswerMapper;
import com.team.puddy.global.mapper.QuestionMapper;
import com.team.puddy.global.mapper.UserMapper;
import com.team.puddy.global.config.security.jwt.JwtTokenUtils;
import com.team.puddy.global.config.security.jwt.LoginToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.team.puddy.global.error.ErrorCode.*;
import static com.team.puddy.global.error.ErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;
    private final QuestionRepository questionRepository;
    private final AnswerQueryRepository answerQueryRepository;

    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    private final UserMapper userMapper;
    private final ImageService imageService;


    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final JwtVerifier jwtVerifier;

    @Transactional
    public LoginToken login(LoginUserRequest request) {
        User user = userRepository.findByAccount(request.account()).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException(INVALID_PASSWORD);
        }

        // 토큰 생성

        return jwtTokenUtils.createLoginToken(user);
    }

    @Transactional
    public void join(RegisterUserRequest request) {
        //회원가입된 유저가 있는지
        userRepository.findByAccount(request.account()).ifPresent(it -> {
            throw new BusinessException(DUPLICATE_ACCOUNT, String.format("%s is duplicated", request.account()));
        });

        //회원가입 진행
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
        //리턴

    }

    @Transactional
    public LoginToken reissueToken(TokenReissueDto tokenReissueDto) {
        String account = jwtVerifier.parseAccount(tokenReissueDto.accessToken());
        jwtVerifier.verifyRefreshToken(account, tokenReissueDto.refreshToken());
        User user = userRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return jwtTokenUtils.createLoginToken(user);
    }


    @Transactional
    public void logout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        jwtVerifier.expireRefreshToken(user.getAccount());
    }

    @Transactional(readOnly = true)
    public ResponseUserInfoDto getUserInfo(Long userId) {
        User findUser = userQueryRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        Image userImage = findUser.getImage();
        boolean hasPet = findUser.getPet() != null;
        if (userImage == null) { // 해당 유저의 이미지가 없는 경우
            return userMapper.toDto(findUser,"",hasPet);
        }
        return userMapper.toDto(findUser, userImage.getImagePath(),hasPet);
    }


    @Transactional
    public void updateProfile(Long userId, String nickname, MultipartFile file) throws IOException {
        User findUser = userQueryRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        Image findImage = findUser.getImage();
        if (file != null && !file.isEmpty()) { //이미지가 있을 경우
            Image savedImage = imageService.uploadImageForUsers(file);

            if (findImage == null) { // 해당 유저의 이미지가 없는 경우
                findUser.setImage(savedImage);
            } else { // 해당 유저의 이미지가 있는 경우
                imageService.deleteImage(findImage);
                findImage.updateImage(savedImage.getImagePath(), savedImage.getOriginalName());
            }
        }
        findUser.setNickname(nickname);
    }

    @Transactional
    public void updateAuth(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        findUser.updateAuth();
    }

    @Transactional(readOnly = true)
    public ResponsePostDto getMyPost(Long userId) {
        List<Question> questionList = questionRepository.findQuestionListByUserId(userId);
        List<Answer> answerList = answerQueryRepository.findAnswerListByUserId(userId);

        List<ResponseQuestionExcludeAnswerDto> questionDtoList = questionList.stream().map(questionMapper::toDto).toList();
        List<ResponseAnswerDtoExcludeUser> answerDtoList = answerList.stream().map(answerMapper::toDto).toList();

        return ResponsePostDto.builder()
                .questionList(questionDtoList)
                .answerList(answerDtoList).build();
    }

    @Transactional(readOnly = true)
    public void duplicateEmailCheck(String email) {
        if (userQueryRepository.isExistsEmail(email)) {
            log.error("중복 에러 발생");
            throw new BusinessException(DUPLICATE_EMAIL, DUPLICATE_EMAIL.getMessage());
        }

    }

    @Transactional(readOnly = true)
    public void duplicateAccountCheck(String account) {
        if (userQueryRepository.isExistsAccount(account)) {
            throw new BusinessException(DUPLICATE_ACCOUNT, DUPLICATE_ACCOUNT.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public void duplicateNicknameCheck(String nickname) {
        if (userQueryRepository.isExistsNickname(nickname)) {
            log.error("중복 에러 발생");
            throw new BusinessException(DUPLICATE_NICKNAME, DUPLICATE_NICKNAME.getMessage());
        }

    }

    @Transactional(readOnly = true)
    public boolean checkHasPet(Long userId) {
        return userQueryRepository.findByIdWithPet(userId).orElseThrow(() -> new BusinessException(USER_NOT_FOUND))
                .getPet() != null; // 펫이 있으면 true, 없으면 false
    }


    @Transactional(readOnly = true)
    public String findAccount(FindAccountDto accountDto) {
        User findUser = userRepository.findByUsernameAndEmail(accountDto.username(), accountDto.email())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return findUser.getAccount();
    }

    @Transactional
    public void changePassword(String password, Long userId) {
        User findUser = userQueryRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(CHANGE_PASSWORD_FAIL));
        findUser.updatePassword(passwordEncoder.encode(password));
    }

}
