package com.shyamanand.bookworm;

import com.shyamanand.bookworm.s3.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

@SpringBootApplication
public class BookWormApplication {

    private static final Logger log = LoggerFactory.getLogger(BookWormApplication.class);

    public static void main(String[] args) {
        System.getProperties().forEach((key, value) -> log.info(key + " -> " + value));
        SpringApplication.run(BookWormApplication.class, args);
    }
}
