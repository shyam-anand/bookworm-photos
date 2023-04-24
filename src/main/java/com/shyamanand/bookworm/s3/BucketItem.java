package com.shyamanand.bookworm.s3;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

public @Data class BucketItem {

    private String key;
    private String owner;
    private Instant date;
    private Long size;
}
