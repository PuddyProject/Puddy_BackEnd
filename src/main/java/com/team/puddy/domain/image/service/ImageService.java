package com.team.puddy.domain.image.service;

import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.image.repository.ImageRepository;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final S3UpdateUtil s3UpdateUtil;

    private final ImageRepository imageRepository;

    public Image uploadImageForQuestions(MultipartFile image) throws IOException {
        String storedFileName = s3UpdateUtil.createFileName(image.getOriginalFilename());
        String imagePath = s3UpdateUtil.uploadQuestionToS3(image, storedFileName);
        return Image.builder().imagePath(imagePath)
                .originalName(image.getOriginalFilename())
                .storedName(storedFileName).build();
    }

    public Image uploadImageForPets(MultipartFile image) throws IOException {
        String storedFileName = s3UpdateUtil.createFileName(image.getOriginalFilename());
        String imagePath = s3UpdateUtil.uploadPetToS3(image, storedFileName);
        return Image.builder().imagePath(imagePath)
                .originalName(image.getOriginalFilename())
                .storedName(storedFileName).build();
    }

    public Image uploadImageForUsers(MultipartFile image) throws IOException {
        String storedFileName = s3UpdateUtil.createFileName(image.getOriginalFilename());
        String imagePath = s3UpdateUtil.uploadUserToS3(image, storedFileName);
        return Image.builder().imagePath(imagePath)
                .originalName(image.getOriginalFilename())
                .storedName(storedFileName).build();
    }

    @Transactional
    public List<Image> saveImageList(List<MultipartFile> images) {
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



    public void deleteImage(Image image) {
        s3UpdateUtil.deleteImage(image);
    }
}
