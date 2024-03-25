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
public class PageSettings {
    @Default
    private String size = "a4";
    @Default
    private float marginleft = 50f;
    @Default
    private float marginright = 50f;
    @Default
    private float margintop = 50f;
    @Default
    private float marginbottom = 50f;
}
