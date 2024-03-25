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
    private PageSettings page = new PageSettings();
    @Default
    private TableSettings table = new TableSettings();
    @Default
    private List<CellData> contents = new ArrayList<>(Collections.singletonList(new CellData()));
}
