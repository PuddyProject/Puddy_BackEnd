package com.team.puddy.global.common;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team.puddy.domain.image.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class S3UpdateUtil {

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET;

    private final AmazonS3Client amazonS3Client;

    public String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(fileName);
    }


    public String uploadQuestionToS3(MultipartFile file,String uuidImageName) throws IOException {

        ObjectMetadata objectMetaData = createMetaDate(file);
        // S3에 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(BUCKET, "questions/" + uuidImageName, file.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return amazonS3Client.getUrl(BUCKET, "questions/" + uuidImageName).toString();
    }

    public String uploadArticleToS3(MultipartFile file,String uuidImageName) throws IOException {

        ObjectMetadata objectMetaData = createMetaDate(file);
        // S3에 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(BUCKET, "questions/" + uuidImageName, file.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return amazonS3Client.getUrl(BUCKET, "questions/" + uuidImageName).toString();
    }


    public String uploadUserToS3(MultipartFile file, String fileName) throws IOException {

        ObjectMetadata objectMetaData = createMetaDate(file);
        // S3에 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(BUCKET, "users/" + fileName, file.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return amazonS3Client.getUrl(BUCKET, "users/" + fileName).toString();
    }

    public String uploadPetToS3(MultipartFile file, String fileName) throws IOException {

        ObjectMetadata objectMetaData = createMetaDate(file);
        // S3에 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(BUCKET, "pets/" + fileName, file.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return amazonS3Client.getUrl(BUCKET, "pets/" + fileName).toString();
    }

    protected static ObjectMetadata createMetaDate(MultipartFile file) {
        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(file.getContentType());
        objectMetaData.setContentLength(file.getSize());
        return objectMetaData;
    }

    public void deleteImage(Image image) {
        amazonS3Client.deleteObject(BUCKET,"questions/" + image.getStoredName());
    }
}
