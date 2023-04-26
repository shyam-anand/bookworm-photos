package com.shyamanand.bookworm.rekognition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.util.Collections;
import java.util.List;

@Service
public class RekognitionService {

    @Autowired
    public RekognitionService(RekognitionClient rekognitionClient) {
        this.rekognitionClient = rekognitionClient;
    }

    private final RekognitionClient rekognitionClient;


    private static final Logger log = LoggerFactory.getLogger(RekognitionService.class);

    public List<String> detectText(byte[] bytes) {

        Image sourceImage = Image.builder()
                .bytes(SdkBytes.fromByteArray(bytes))
                .build();

        DetectTextRequest request = DetectTextRequest.builder()
                .image(sourceImage)
                .build();

        try {
            DetectTextResponse result = rekognitionClient.detectText(request);
            if (result.hasTextDetections()) {
                log.info(result.textDetections().size() + " text detections.");
                return getDetectedText(result);
            } else {
                return Collections.emptyList();
            }
        } catch (RekognitionException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getDetectedText(DetectTextResponse result) {
        return result.textDetections().stream()
                .filter(textDetection -> textDetection.confidence() > 99.0f)
                .map(TextDetection::detectedText)
                .toList();
    }
}