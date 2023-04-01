package com.team.puddy.domain;

import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.dto.RequestExpertDto;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.dto.request.QuestionRequestDto;
import com.team.puddy.domain.question.dto.response.QuestionResponeDtoExcludeAnswer;
import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import com.team.puddy.domain.type.Category;
import com.team.puddy.domain.type.UserRole;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.dto.request.RegisterUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

public class TestEntityUtils {


    public static User user() {
        return User.builder()
                .id(1L)
                .username("상준")
                .account("user1")
                .password("1234")
                .nickname("퍼디1234")
                .email("test@email.com")
                .role(UserRole.USER.getRole())
                .build();
    }

    public static Question question(User user) {
        return Question.builder()
                .id(13L)
                .title("title")
                .content("content")
                .answerList(List.of())
                .category(Category.짖기)
                .imagePath("")
                .user(user)
                .isSolved(false)
                .postCategory(2)
                .viewCount(2L)
                .isDeleted(false)
                .build();
    }

    public static QuestionRequestDto questionRequestDto() {
        return QuestionRequestDto.builder()
                .title("test title")
                .content("test content")
                .postCategory(2)
                .category("먹이")
                .build();
    }

    public static RegisterUserRequest createRequestDto() {
        RegisterUserRequest request = RegisterUserRequest.builder()
                .account("puddy")
                .username("username")
                .email("email@test.com")
                .isNotificated(true)
                .password("1234")
                .build();
        return request;
    }

    public static RequestExpertDto requestExpertDto() {

        return RequestExpertDto.builder().
                education("테스트대학교")
                        .introduce("저는 테스트입니다.")
                        .location("테스트시 테스트동")
                        .careerList(List.of("테스트 대학원 석사", "테스트 대학교 수의학과 학사"))
                        .build();

    }

    public static List<QuestionResponeDtoExcludeAnswer> questionList() {
        User user = user();
        Question question1 = question(user);
        Question question2 = question(user);
        Question question3 = question(user);
        Question question4 = question(user);
        Question question5 = question(user);
        List<Question> questions = List.of(question1, question2, question3, question4, question5);
        return questions.stream().map(question -> QuestionResponeDtoExcludeAnswer.builder().questionId(question.getId())
                .nickname(question.getUser().getNickname())
                .postCategory(question.getPostCategory())
                .title(question.getTitle())
                .content(question.getContent())
                .createdDate(question.getCreatedDate())
                .category(question.getCategory().name()).build()).toList();
    }

    public static Page<QuestionResponeDtoExcludeAnswer> questionPageList() {
        List<QuestionResponeDtoExcludeAnswer> questions = questionList();
        PageRequest page = PageRequest.of(1, 10);

        return new PageImpl<>(questions, page, questions.size());
    }

    public static Expert expert() {
        return Expert.builder().id(2L)
                .education("테스트대학교")
                .introduce("저는 테스트입니다.")
                .location("테스트시 테스트동")
                .careerList(List.of("테스트 대학원 석사", "테스트 대학교 수의학과 학사"))
                .user(null).build();
    }
}
