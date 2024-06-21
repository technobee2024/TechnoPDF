package com.technobee.technopdf.lib;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;

public class PDF {
    public static String DEFAULT_TEXT_COLOR = "0,0,0";
    public static String DEFAULT_BACK_COLOR = "255,255,255";
    public static String DEFAULT_BORDER = "0,0,0,0";
    public static String DEFAULT_PADDING = "5,3,5,3";
    public static String DEFAULT_BORDER_COLOR = "0,0,0";

    public enum Size {
        A4("a4", PageSize.A4);
    
        private final String size;
        private final Rectangle dimension;
    
        Size(String size, Rectangle dimension) {
            this.size = size;
            this.dimension = dimension;
        }
    
        public static Rectangle getSize(String size) {
            for (Size pageSize : Size.values()) {
                if (pageSize.size.equalsIgnoreCase(size)) {
                    return pageSize.dimension;
                }
            }
            return PageSize.A4;
        }
    }
    public enum CellType {
        TEXT("text"),
        IMAGE("image"),
        LINEBREAK("linebreak"),
        PAGEBREAK("pagebreak"),
        BARCODE("barcode"),
        QRCODE("qrcode"),
        REPORT("report");
    
        private final String type;
    
        CellType(String type) {
            this.type = type;
        }
    }
    public enum TextStyle {
        NORMAL(Font.NORMAL),
        BOLD(Font.BOLD),
        ITALIC(Font.ITALIC),
        BOLDITALIC(Font.BOLDITALIC);
    
        public final int style;
    
        TextStyle(int style) {
            this.style = style;
        }

        public static int getStyle(String stylename){
            for (TextStyle name : TextStyle.values()) {
                if (name.name().equalsIgnoreCase(stylename)) {
                    return name.style;
                }
            }
            return TextStyle.NORMAL.style;
        }
    }
    public enum FontFamily {
        TIMES(Font.TIMES_ROMAN),
        HELVETICA(Font.HELVETICA),
        TAHOMA(102);
    
        private final int family;
    
        FontFamily(int family) {
            this.family = family;
        }

        public static int getFamily(String fontname){
            for (FontFamily name : FontFamily.values()) {
                if (name.name().equalsIgnoreCase(fontname)) {
                    return name.family;
                }
            }
            return FontFamily.HELVETICA.family;
        }
    }
    public enum CellAlignment {
        LEFT(Element.ALIGN_LEFT),
        TOP(Element.ALIGN_TOP),
        RIGHT(Element.ALIGN_RIGHT),
        BOTTOM(Element.ALIGN_BOTTOM),
        CENTER(Element.ALIGN_CENTER),
        MIDDLE(Element.ALIGN_MIDDLE),
        BASELINE(Element.ALIGN_BASELINE),
        JUSTIFIED(Element.ALIGN_JUSTIFIED),
        JUSTIFIEDALL(Element.ALIGN_JUSTIFIED_ALL);
    
        private final int alignment;
    
        CellAlignment(int alignment) {
            this.alignment = alignment;
        }

        public static int getAlignment(String alignment){
            for (CellAlignment align : CellAlignment.values()) {
                if (align.name().equalsIgnoreCase(alignment)) {
                    return align.alignment;
                }
            }
            return FontFamily.HELVETICA.family;
        }
    }
}
