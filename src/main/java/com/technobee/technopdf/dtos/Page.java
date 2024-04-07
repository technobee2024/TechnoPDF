package com.technobee.technopdf.dtos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page {
    @Default
    private TableSettings table = new TableSettings();
    @Default
    private List<CellData> contents = new ArrayList<>(Collections.singletonList(new CellData()));
}
