package com.shyamanand.bookworm.rest;

import com.shyamanand.bookworm.rekognition.RekognitionService;
import com.shyamanand.bookworm.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/photos")
public class PhotosController {

    private final RekognitionService rekognitionService;
    private final S3Service s3Service;

    @Autowired
    public PhotosController(RekognitionService rekognitionService,
                            S3Service s3Service) {
        this.rekognitionService = rekognitionService;
        this.s3Service = s3Service;
    }

    @GetMapping("/{key}/text")
    public ResponseEntity<?> detectText(@PathVariable String key) {
        byte[] imageBytes = s3Service.getObjectBytes(key);
        List<String> detectedText = rekognitionService.detectText(imageBytes);
        return ResponseEntity.ok(detectedText);
    }

    @GetMapping
    public @ResponseBody List<Photo> getPhotos() {
        return s3Service.getAllObjects().stream()
                .map(Photo::fromS3Object)
                .toList();
    }

    @PostMapping
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            return ResponseEntity.ok(s3Service.addPhoto(bytes));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(e.getMessage());
        }
    }
}