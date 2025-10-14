package com.technobee.technopdf.dtos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PDFRequest {
    @Default
    private PageSettings pagesettings = new PageSettings();
    @Default
    private List<Page> pages = new ArrayList<>(Collections.singletonList(new Page()));
    @Default
    private WatermarkSettings watermark = new WatermarkSettings();
}
