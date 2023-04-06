package com.team.puddy.domain.image.service;

import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.image.repository.ImageRepository;
import com.team.puddy.global.common.S3UpdateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3UpdateUtil s3UpdateUtil;

    private final ImageRepository imageRepository;

    public Image uploadImageForQuestions(MultipartFile image) throws IOException {
        String storedFileName = s3UpdateUtil.createFileName(image.getOriginalFilename());
        String imagePath = s3UpdateUtil.uploadQuestionToS3(image, storedFileName);
        return Image.builder().imagePath(imagePath)
                .originalName(image.getOriginalFilename()).build();
    }

    public Image uploadImageForPets(MultipartFile image) throws IOException {
        String storedFileName = s3UpdateUtil.createFileName(image.getOriginalFilename());
        String imagePath = s3UpdateUtil.uploadPetToS3(image, storedFileName);
        return Image.builder().imagePath(imagePath)
                .originalName(image.getOriginalFilename()).build();
    }

    public Image uploadImageForUsers(MultipartFile image) throws IOException {
        String storedFileName = s3UpdateUtil.createFileName(image.getOriginalFilename());
        String imagePath = s3UpdateUtil.uploadUserToS3(image, storedFileName);
        return Image.builder().imagePath(imagePath)
                .originalName(image.getOriginalFilename()).build();
    }

    private Image saveImage(String originalName, String imagePath) {
        Image image = Image.builder()
                .imagePath(imagePath)
                .originalName(originalName)
                .build();

        return imageRepository.save(image);
    }
}
