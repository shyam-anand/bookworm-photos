package com.shyamanand.bookworm.rest;

import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.services.s3.model.S3Object;

@Builder
public @Data class Photo {

    private String name;
    private String owner;
    private String eTag;

    public static Photo fromS3Object(S3Object s3Object) {
        return Photo.builder()
                .name(s3Object.key())
                .eTag(s3Object.eTag())
                .owner(s3Object.owner().displayName())
                .build();
    }
}
