package com.team.puddy.global.common;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team.puddy.domain.image.domain.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Component
@RequiredArgsConstructor
@Slf4j
public class S3UpdateUtil {

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET;

    private final AmazonS3Client amazonS3Client;

    public String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(fileName);
    }

    public String uploadToS3(MultipartFile file, String fileName, String folderName) throws IOException {

        ObjectMetadata objectMetaData = createMetaDate(file);
        // S3에 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(BUCKET, folderName + "/" + fileName, file.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return amazonS3Client.getUrl(BUCKET, folderName + "/" + fileName).toString();
    }

    public void deleteImageFromS3(Image image, String folderName) {
        log.info(folderName+"/"+image.getStoredName());
        amazonS3Client.deleteObject(BUCKET, folderName + "/" + image.getStoredName());
    }

    protected static ObjectMetadata createMetaDate(MultipartFile file) {
        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(file.getContentType());
        objectMetaData.setContentLength(file.getSize());
        return objectMetaData;
    }


}
