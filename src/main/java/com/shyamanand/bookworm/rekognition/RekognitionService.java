package com.shyamanand.bookworm.rekognition;

import com.shyamanand.bookworm.rest.AnalysisResult;
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

    public List<AnalysisResult> detectLabels(byte[] bytes) {

        try {
            SdkBytes sourceBytes = SdkBytes.fromByteArray(bytes);

            Image sourceImage = Image.builder()
                    .bytes(sourceBytes)
                    .build();

            DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder()
                    .image(sourceImage)
                    .maxLabels(10)
                    .build();

            DetectLabelsResponse labelsResponse = rekognitionClient.detectLabels(detectLabelsRequest);

            List<Label> labels = labelsResponse.labels();

            return labels.stream()
                    .map(AnalysisResult::fromLabel)
                    .toList();

        } catch (RekognitionException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> detectText(byte[] bytes) {

        Image sourceImage = Image.builder()
                .bytes(SdkBytes.fromByteArray(bytes))
                .build();

        DetectTextRequest request = DetectTextRequest.builder()
                .image(sourceImage)
                .build();

        String detectTextRequest = request.toString();
        try {
            log.info("Sending detectTextRequest: " + detectTextRequest);
            DetectTextResponse result = rekognitionClient.detectText(request);
            log.info("Received result for " + detectTextRequest + ": " + result.hasTextDetections());
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
