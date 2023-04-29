package com.shyamanand.bookworm.rekognition;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;

@Configuration
public class RekognitionConfig {

    @Bean
    public Region rekognitionRegion() {
        return Region.AP_SOUTH_1;
    }

    @Bean
    public RekognitionClient rekognitionClient(Region rekognitionRegion) {

        return RekognitionClient.builder()
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .region(rekognitionRegion)
                .build();
    }

}
