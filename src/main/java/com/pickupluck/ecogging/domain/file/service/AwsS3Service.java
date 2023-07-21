package com.pickupluck.ecogging.domain.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pickupluck.ecogging.domain.file.dto.AwsS3Dto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public AwsS3Dto upload(MultipartFile multipartFile, String dirName) throws IOException {
        File file = convertMultipartFileToFile(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("Failed to convert MultipartFile to File"));

        return upload(file, dirName);
    }

    private AwsS3Dto upload(File file, String dirName) {
        String objectKey = getRandomFileName(file, dirName);
        String absolutPath = putS3(file, objectKey);
        removeFile(file);

        return AwsS3Dto.builder()
                .objectKey(objectKey)
                .absolutPath(absolutPath)
                .build();
    }

    private String getRandomFileName(File file, String dirName) {
        return dirName + "/" + UUID.randomUUID() + file.getName();
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return getS3(bucket, fileName);
    }

    private String getS3(String bucket, String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeFile(File file) {
        if (Objects.nonNull(file))
            file.delete();
    }

    public Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)){
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }

    public void remove(AwsS3Dto awsS3) {
        if (!amazonS3.doesObjectExist(bucket, awsS3.getObjectKey())) {
            throw new AmazonS3Exception("S3 Object " +awsS3.getObjectKey()+ " does not exist.");
        }

        amazonS3.deleteObject(bucket, awsS3.getObjectKey());
    }
}