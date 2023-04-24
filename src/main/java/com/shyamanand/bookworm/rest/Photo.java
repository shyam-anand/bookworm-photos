package com.shyamanand.bookworm.rest;

import lombok.Data;
import software.amazon.awssdk.services.s3.model.S3Object;

public @Data class Photo {

    private String name;
    private String owner;
    private String eTag;

    public static Photo fromS3Object(S3Object s3Object) {
        Photo photo = new Photo();
        photo.setName(s3Object.key());
        photo.setOwner(s3Object.owner().displayName());
        photo.setETag(s3Object.eTag());
        return photo;
    }
}
