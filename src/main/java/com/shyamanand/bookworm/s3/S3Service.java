package com.shyamanand.bookworm.s3;

import com.shyamanand.bookworm.rest.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.UUID;

@Service
public class S3Service {

    private static final Logger log = LoggerFactory.getLogger(S3Service.class);

    private final S3Client s3Client;
    private final String s3Bucket;

    @Autowired
    public S3Service(S3Client s3Client, String s3Bucket) {
        this.s3Client = s3Client;
        this.s3Bucket = s3Bucket;
    }

    public byte[] getObjectBytes(String key) {

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .key(key)
                    .bucket(s3Bucket)
                    .build();
            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
            return objectBytes.asByteArray();
        } catch (S3Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<S3Object> getAllObjects() {
        try {
            ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                    .bucket(s3Bucket)
                    .build();

            return s3Client.listObjects(listObjectsRequest)
                    .contents();
        } catch (S3Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Photo addPhoto(byte[] bytes) {
        String key = UUID.randomUUID().toString();
        log.info("Writing file to S3. key = " + key);
        try {
            PutObjectResponse response = s3Client.putObject(PutObjectRequest.builder()
                            .bucket(s3Bucket)
                            .key(key)
                            .build(),
                    RequestBody.fromBytes(bytes));
            return Photo.builder()
                    .name(key)
                    .eTag(response.eTag())
                    .build();
        } catch (S3Exception e) {
            throw new RuntimeException(e);
        }
    }
}
