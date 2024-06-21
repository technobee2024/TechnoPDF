package com.technobee.technopdf.dtos;

import com.technobee.technopdf.lib.PDF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CellData {
    private Object data;
    @Default
    private Page subreport = null;
    @Default
    private String type = PDF.CellType.TEXT.name();
    @Default
    private CellSettings settings = new CellSettings();
}
