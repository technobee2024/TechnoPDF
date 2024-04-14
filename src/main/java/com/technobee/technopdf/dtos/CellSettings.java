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
public class CellSettings {
    @Default
    private int rowspan = 1;
    @Default
    private int colspan = 100;
    @Default
    private String family = PDF.FontFamily.HELVETICA.name();
    @Default
    private float fontsize = 12;
    @Default
    private String fontstyle = PDF.TextStyle.NORMAL.name();
    @Default
    private String colorrgb = PDF.DEFAULT_TEXT_COLOR;
    @Default
    private String border = PDF.DEFAULT_BORDER;
    @Default
    private String textalign = PDF.CellAlignment.LEFT.name();
    @Default
    private String textvalign = PDF.CellAlignment.MIDDLE.name();
    @Default
    private String padding = PDF.DEFAULT_PADDING;
    @Default
    private String backgroundrgb = PDF.DEFAULT_BACK_COLOR;
    @Default
    private String bordercolorrgb = PDF.DEFAULT_BORDER_COLOR;
    @Default
    private float indentation = 0;
    @Default
    private float leading = 0;
    @Default
    private boolean underline = false;
    @Default
    private boolean strikethrough = false;
}
