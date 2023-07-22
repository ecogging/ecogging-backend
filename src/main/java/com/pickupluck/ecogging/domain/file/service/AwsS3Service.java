package com.pickupluck.ecogging.domain.file.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

import com.pickupluck.ecogging.domain.file.dto.AwsS3Dto;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

//
//    public AwsS3Dto upload(MultipartFile multipartFile, String dirName) throws IOException {
//        File file = convertMultipartFileToFile(multipartFile)
//                .orElseThrow(() -> new IllegalArgumentException("Failed to convert MultipartFile to File"));
//
//        return upload(file, dirName);
//    }

    public AwsS3Dto upload(MultipartFile file, String dirName) throws IOException {
        String objectKey = getRandomFileName(file, dirName);
        String absolutPath = putS3(file, objectKey);

        return AwsS3Dto.builder()
                .objectKey(objectKey)
                .absolutPath(absolutPath)
                .build();
    }

    private String getRandomFileName(MultipartFile file, String dirName) {
        return dirName + "/" + UUID.randomUUID() + file.getName();
    }

    private String putS3(MultipartFile uploadFile, String fileName) throws IOException{
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile.getInputStream(), new ObjectMetadata())
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return getS3(bucket, fileName);
    }

    private String getS3(String bucket, String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }


    public Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
        return Optional.of(file);
    }

    public void remove(AwsS3Dto awsS3) {
        if (!amazonS3.doesObjectExist(bucket, awsS3.getObjectKey())) {
            throw new AmazonS3Exception("S3 Object " +awsS3.getObjectKey()+ " does not exist.");
        }

        amazonS3.deleteObject(bucket, awsS3.getObjectKey());
    }

    public String getExistImageUrl(String fileName) {
        if (!amazonS3.doesObjectExist(bucket, fileName)) {
            throw new AmazonS3Exception("S3 Object " + fileName + " does not exist.");
        }
        String s3FileName = getS3(bucket, fileName);
        return s3FileName;
    }
}