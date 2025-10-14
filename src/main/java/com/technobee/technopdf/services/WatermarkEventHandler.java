package com.technobee.technopdf.services;

import java.awt.Color;
import java.io.IOException;
import java.net.URI;

import org.springframework.core.io.ClassPathResource;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import com.technobee.technopdf.dtos.WatermarkSettings;
import com.technobee.technopdf.lib.PDF;

public class WatermarkEventHandler extends PdfPageEventHelper {
    private WatermarkSettings watermarkSettings;

    public WatermarkEventHandler(WatermarkSettings watermarkSettings) {
        this.watermarkSettings = watermarkSettings;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        if (!watermarkSettings.isEnabled()) {
            return;
        }

        PdfContentByte canvas = writer.getDirectContentUnder();
        PdfGState gState = new PdfGState();
        gState.setFillOpacity(watermarkSettings.getOpacity());
        gState.setBlendMode(PdfGState.BM_NORMAL);
        canvas.setGState(gState);

        float pageWidth = document.getPageSize().getWidth();
        float pageHeight = document.getPageSize().getHeight();

        if (!watermarkSettings.getImageUrl().isEmpty()) {
            // Image watermark
            try {
                URI uri = URI.create(watermarkSettings.getImageUrl());
                Image img = Image.getInstance(uri.toURL());

                // Scale image
                float targetW = watermarkSettings.getImageTargetWidth();
                float targetH = watermarkSettings.getImageTargetHeight();
                float percent = watermarkSettings.getImageSizePercentage();

                float origW = img.getWidth();
                float origH = img.getHeight();

                if (targetW > 0 && targetH > 0) {
                    img.scaleAbsolute(targetW, targetH);
                } else if (targetW > 0) {
                    float newH = (origH * targetW) / origW;
                    img.scaleAbsolute(targetW, newH);
                } else if (targetH > 0) {
                    float newW = (origW * targetH) / origH;
                    img.scaleAbsolute(newW, targetH);
                } else if (percent > 0) {
                    img.scalePercent(percent);
                } else {
                    img.scaleToFit(200f, 200f);
                }

                // Center the image
                float x = (pageWidth - img.getScaledWidth()) / 2;
                float y = (pageHeight - img.getScaledHeight()) / 2;

                img.setAbsolutePosition(x, y);
                canvas.addImage(img);
            } catch (Exception e) {
                // Fallback to text if image fails
                addTextWatermark(canvas, pageWidth, pageHeight);
            }
        } else {
            // Text watermark
            addTextWatermark(canvas, pageWidth, pageHeight);
        }
    }

    private void addTextWatermark(PdfContentByte canvas, float pageWidth, float pageHeight) {
        Font font = null;
        try {
            font = FontFactory.getFont("Helvetica", BaseFont.WINANSI, watermarkSettings.getFontSize(), Font.NORMAL, Color.GRAY);
        } catch (Exception e) {
            font = new Font(Font.HELVETICA, watermarkSettings.getFontSize(), Font.NORMAL, Color.GRAY);
        }

        canvas.saveState();
        canvas.beginText();
        canvas.setFontAndSize(font.getBaseFont(), watermarkSettings.getFontSize());
        canvas.setColorFill(new Color(128, 128, 128, (int)(watermarkSettings.getOpacity() * 255)));

        // Center the text and apply rotation
        float textWidth = font.getBaseFont().getWidthPoint(watermarkSettings.getText(), watermarkSettings.getFontSize());
        float textHeight = font.getSize();
        float x = pageWidth / 2;
        float y = pageHeight / 2;

        // Apply rotation transformation
        canvas.concatCTM((float) Math.cos(Math.toRadians(watermarkSettings.getRotation())),
                        (float) Math.sin(Math.toRadians(watermarkSettings.getRotation())),
                        (float) -Math.sin(Math.toRadians(watermarkSettings.getRotation())),
                        (float) Math.cos(Math.toRadians(watermarkSettings.getRotation())),
                        x, y);

        // Position text at center (accounting for rotation)
        canvas.moveText(-textWidth / 2, -textHeight / 2);
        canvas.showText(watermarkSettings.getText());
        canvas.endText();
        canvas.restoreState();
    }
}