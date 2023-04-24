package com.shyamanand.bookworm.s3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetBucketLocationRequest;
import software.amazon.awssdk.services.s3.model.GetBucketLocationResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

@Configuration
public class S3Config {

    private static final Logger log = LoggerFactory.getLogger(S3Config.class);

    @Bean
    public Region awsRegion() {
        return Region.US_EAST_1;
    }

    @Bean
    public String s3Bucket() {
        return "bookworm-userphotos";
    }

    @Bean
    public S3Client s3Client(Region awsRegion) {
        return S3Client.builder()
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .region(awsRegion)
                .build();

    }

    @Bean
    public Boolean s3AccessChecker(S3Client s3Client, String s3Bucket) {
        GetBucketLocationResponse bucketLocation = s3Client.getBucketLocation(
                GetBucketLocationRequest.builder()
                        .bucket(s3Bucket).build());
        log.info(s3Bucket + " is located at " +
                bucketLocation.locationConstraint().name());
        return true;
    }
}
