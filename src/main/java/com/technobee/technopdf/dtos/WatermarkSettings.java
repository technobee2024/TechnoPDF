package com.technobee.technopdf.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WatermarkSettings {
    @Default
    private boolean enabled = false;
    @Default
    private String text = "SAMPLE";
    @Default
    private float fontSize = 60f;
    @Default
    private float opacity = 0.15f;
    @Default
    private float rotation = 45f; // degrees
    @Default
    private String imageUrl = ""; // if set, use image watermark instead of text
    @Default
    private float imageTargetWidth = 0; // in points
    @Default
    private float imageTargetHeight = 0; // in points
    @Default
    private float imageSizePercentage = 0; // percent for image scaling (e.g., 50 for 50%)
}
