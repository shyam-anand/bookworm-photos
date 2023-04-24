package com.shyamanand.bookworm.rest;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.rekognition.model.Label;
import software.amazon.awssdk.services.rekognition.model.LabelCategory;

import java.util.List;

@NoArgsConstructor
public @Data class AnalysisResult {
    private String name;
    private Float confidence;
    private List<String> categories;

    public static AnalysisResult fromLabel(Label label) {
        AnalysisResult result = new AnalysisResult();
        result.setName(label.name());
        result.setConfidence(label.confidence());
        result.setCategories(label.categories().stream().map(LabelCategory::name).toList());
        return result;
    }
}
