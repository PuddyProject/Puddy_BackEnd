package com.team.puddy.domain.image.service;

import com.team.puddy.domain.article.domain.Article;
import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.pet.domain.Pet;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.global.common.S3UpdateUtil;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final S3UpdateUtil s3UpdateUtil;


    private Image uploadImage(MultipartFile file, BiFunction<MultipartFile, String, String> uploadFunction) throws IOException {
        String storedFileName = s3UpdateUtil.createFileName(file.getOriginalFilename());
        String imagePath = uploadFunction.apply(file, storedFileName);
        return Image.builder().imagePath(imagePath)
                .originalName(file.getOriginalFilename())
                .storedName(storedFileName).build();
    }

    public Image uploadImageForQuestions(MultipartFile image) throws IOException {
        return uploadImage(image, (img, storedName) -> {
            try {
                return s3UpdateUtil.uploadToS3(img, storedName, "questions");
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.IMAGE_PROCESSING_ERROR);
            }
        });
    }

    public Image uploadImageForPets(MultipartFile image) throws IOException {
        return uploadImage(image, (img, storedName) -> {
            try {
                return s3UpdateUtil.uploadToS3(img, storedName, "pets");
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.IMAGE_PROCESSING_ERROR);
            }
        });
    }

    public Image uploadImageForUsers(MultipartFile image) throws IOException {
        return uploadImage(image, (img, storedName) -> {
            try {
                return s3UpdateUtil.uploadToS3(img, storedName, "users");
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.IMAGE_PROCESSING_ERROR);
            }
        });
    }

    public Image uploadImageForArticles(MultipartFile image) throws IOException {
        return uploadImage(image, (img, storedName) -> {
            try {
                return s3UpdateUtil.uploadToS3(img, storedName, "articles");
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.IMAGE_PROCESSING_ERROR);
            }
        });
    }

    @Transactional
    public List<Image> saveImageListForQuestion(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return new ArrayList<>();
        }

        return images.stream()
                .map(multipartFile -> {
                    try {
                        return uploadImageForQuestions(multipartFile);
                    } catch (IOException e) {
                        // 예외 처리 코드 (예: 로그 남기기)
                        throw new BusinessException(ErrorCode.IMAGE_PROCESSING_ERROR);
                    }
                })
                .toList();
    }

    @Transactional
    public List<Image> saveImageListToArticle(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return new ArrayList<>();
        }

        return images.stream()
                .map(multipartFile -> {
                    try {
                        return uploadImageForArticles(multipartFile);
                    } catch (IOException e) {
                        // 예외 처리 코드 (예: 로그 남기기)
                        throw new BusinessException(ErrorCode.IMAGE_PROCESSING_ERROR);
                    }
                })
                .toList();
    }

    public void deleteImageQuestion(Image image) {
        s3UpdateUtil.deleteImageFromS3(image,"questions");
    }
    public void deleteImageArticle(Image image) {
        s3UpdateUtil.deleteImageFromS3(image,"articles");
    }
    public void deleteImagePet(Image image) {
        s3UpdateUtil.deleteImageFromS3(image, "pets");
    }
    public void deleteImageUser(Image findImage) {
        s3UpdateUtil.deleteImageFromS3(findImage,"users");
    }
    public void deleteImageListFromQuestion(List<Image> findImages) {
        findImages.forEach(this::deleteImageQuestion);
    }

    public void deleteImageListFromArticle(List<Image> findImages) {
        findImages.forEach(this::deleteImageArticle);
    }

    public void updateImageListForQuestion(Question findQuestion, List<MultipartFile> images) {
        //기존에 저장된 이미지를 지운다.
        List<Image> findImages = findQuestion.getImageList();
        if (findImages != null) {
            deleteImageListFromQuestion(findImages);
        }
        //새로운 이미지를 저장한다.
        List<Image> imageList = saveImageListForQuestion(images);
        //질문글을 수정한다.
        findQuestion.updateImageList(imageList);
    }

    public void updateImageListForArticle(Article findArticle, List<MultipartFile> images) {
        //기존에 저장된 이미지를 지운다.
        List<Image> findImages = findArticle.getImageList();
        if (findImages != null) {
            deleteImageListFromArticle(findImages);
        }
        //새로운 이미지를 저장한다.
        List<Image> imageList = saveImageListToArticle(images);
        //질문글을 수정한다.
        findArticle.updateImageList(imageList);
    }
    
    public void updateImageUser(User findUser, MultipartFile file) throws IOException {
        //기존에 저장된 이미지를 지운다.
        Image findImage = findUser.getImage();
        if (findImage != null) {
            deleteImageUser(findImage);
        }
        //새로운 이미지를 저장한다.
        saveImageUser(findUser, file);
    }

    public void saveImageUser(User user, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) { //이미지가 있을 경우
            Image savedimage = uploadImageForUsers(file);
            user.setImage(savedimage);
        } else { // 이미지가 없을 경우
            user.setImage(null);
        }
    }

    public void updateImagePet(Pet findPet, MultipartFile file) throws IOException {
        //기존에 저장된 이미지를 지운다.
        Image findImage = findPet.getImage();
        if (findImage != null) {
            deleteImagePet(findImage);
        }
        //새로운 이미지를 저장한다.
        saveImagePet(findPet, file);
    }


    public void saveImagePet(Pet pet, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) { //이미지가 있을 경우
            Image savedimage = uploadImageForPets(file);
            pet.setImage(savedimage);
        } else { // 이미지가 없을 경우
            pet.setImage(null);
        }
    }
}
