package com.pickupluck.ecogging.domain.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwsS3Dto {

    private String objectKey;

    private String absolutPath;

    public AwsS3Dto() {}

    @Builder
    public AwsS3Dto(String objectKey, String absolutPath) {
        this.objectKey = objectKey;
        this.absolutPath = absolutPath;
    }
}