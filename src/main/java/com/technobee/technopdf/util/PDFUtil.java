package com.technobee.technopdf.util;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.technobee.technopdf.lib.PDF;

public class PDFUtil {
    private static final String RGB_PATTERN = "^\\d{1,3},\\d{1,3},\\d{1,3}$";

    public static Color getRGBColor(String colorRGB, String fallbackRGB) {
        Pattern pattern = Pattern.compile(RGB_PATTERN);
        Matcher matcher = pattern.matcher(colorRGB);
        String[] components = null;
        int red = 0;
        int green = 0;
        int blue = 0;
        boolean isvalid = false;
        if (matcher.matches()) {
            components = colorRGB.split(",");
            red = Integer.parseInt(components[0]);
            green = Integer.parseInt(components[1]);
            blue = Integer.parseInt(components[2]);
            isvalid = red >= 0 && red <= 255 &&
                   green >= 0 && green <= 255 &&
                   blue >= 0 && blue <= 255;
        }
        if(!isvalid){
            components = fallbackRGB.split(",");
            red = Integer.parseInt(components[0]);
            green = Integer.parseInt(components[1]);
            blue = Integer.parseInt(components[2]);
        }
        return new Color(red, green, blue);
    }

    public static float[] getBorders(String border) {
        String[] defaultborders = PDF.DEFAULT_BORDER.split(",");
        String[] arrborder = border.split(",");
        float[] borders = new float[4];
        for (int i = 0; i < 4; i++) {
            float width = Float.parseFloat(defaultborders[i].trim());
            if(i <= (arrborder.length-1)){
                try{
                    width = Float.parseFloat(arrborder[i].trim());
                }catch(NumberFormatException nfe){
                    //Do nothing and use default border width in case of number format exception;
                }
            }
            borders[i] = width;
        }
        return borders;
    }

    public static float[] getPaddings(String cellpadding) {
        String[] defaultpaddings = PDF.DEFAULT_PADDING.split(",");
        String[] arrpadding = cellpadding.split(",");
        float[] paddings = new float[4];
        for (int i = 0; i < 4; i++) {
            float padding = Float.parseFloat(defaultpaddings[i].trim());
            if(i <= (arrpadding.length-1)){
                try{
                    padding = Float.parseFloat(arrpadding[i].trim());
                }catch(NumberFormatException nfe){
                    //Do nothing and use default border width in case of number format exception;
                }
            }
            paddings[i] = padding;
        }
        return paddings;
    }
}
