package com.technobee.technopdf.services;

import java.awt.Color;
import java.io.ByteArrayInputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.technobee.technopdf.dtos.CellData;
import com.technobee.technopdf.dtos.CellSettings;
import com.technobee.technopdf.dtos.PageSettings;
import com.technobee.technopdf.dtos.TableSettings;
import com.technobee.technopdf.lib.PDF;
import com.technobee.technopdf.util.PDFUtil;

public class PDFDocument {
    Document document = null;
    PdfPTable table = null;
    protected void initializeDocument(PageSettings page){
        FontFactory.register(new ClassPathResource("fonts/tahoma.ttf").getPath());
        FontFactory.register(new ClassPathResource("fonts/tahomabd.ttf").getPath());
        document = new Document(PDF.Size.getSize(page.getSize()), page.getMarginleft(), page.getMarginright(), page.getMargintop(), page.getMarginbottom());
    }
    protected void initializeTable(TableSettings tablesettings){
        table = new PdfPTable(tablesettings.getColumns());
        table.setWidthPercentage(100);
    }
    protected void appendCell(CellData cellData){
        if(cellData.getType().equals(PDF.CellType.TEXT.name())){
            createTextCell(cellData);
        }
    }
    protected void createTextCell(CellData cellData){
        CellSettings settings = cellData.getSettings();
        Paragraph celltext = createParagraph(cellData.getData().toString(), settings);
        PdfPCell cell = new PdfPCell(celltext);
        cell.setColspan(settings.getColspan());
        cell.setRowspan(settings.getRowspan());
        setCellBorder(cell, settings);
        setCellPadding(cell, settings);
        cell.setBackgroundColor(PDFUtil.getRGBColor(settings.getBackgroundrgb(), PDF.DEFAULT_BACK_COLOR));
        cell.setVerticalAlignment(PDF.CellAlignment.getAlignment(settings.getTextvalign()));
        cell.setHorizontalAlignment(PDF.CellAlignment.getAlignment(settings.getTextalign()));
        cell.setUseBorderPadding(true);
        table.addCell(cell);
    }
    protected Paragraph createParagraph(String data, CellSettings settings){
        Font font = null;
        if(settings.getFamily().equals(PDF.FontFamily.TAHOMA.name())){
            font = FontFactory.getFont("Tahoma", BaseFont.WINANSI, settings.getFontsize(), PDF.TextStyle.getStyle(settings.getFontstyle()), PDFUtil.getRGBColor(settings.getColorrgb(), PDF.DEFAULT_TEXT_COLOR));
        }else{
            font = new Font(PDF.FontFamily.getFamily(
                settings.getFamily()), 
                settings.getFontsize(), 
                PDF.TextStyle.getStyle(settings.getFontstyle()),
                PDFUtil.getRGBColor(settings.getColorrgb(), PDF.DEFAULT_TEXT_COLOR)
            );
        }

        if (settings.isUnderline()) {
            font.setStyle(font.getStyle() | Font.UNDERLINE);
        }

        if (settings.isStrikethrough()) {
            font.setStyle(font.getStyle() | Font.STRIKETHRU);
        }
        Paragraph p = new Paragraph(StringUtils.EMPTY,font);
        if(data.contains("<b>")){
            setChunksBold(p, data, font);
        }else{
            p = new Paragraph(data, font);
        }
        if(settings.getIndentation() > 0){
            p.setLeading(0, 2f);
        }
        return p;
    }
    private void setChunksBold(Paragraph p, String data, Font font) {
        int defaultStyle = font.getStyle();
        String[] words = data.split(" ");
        int wordindex = 0;
        boolean isbold = false;
        for (String word : words) {
            if(word.startsWith("<b>")){
                isbold = true;
                font.setStyle(PDF.TextStyle.BOLD.style);
                word = word.replaceAll("<b>", "");
            }
            if(word.endsWith("</b>")){
                word = word.replaceAll("</b>", "");
                isbold = false;
            }
            wordindex++;
            word = wordindex == words.length ? word : word + " ";
            p.add(new Chunk(word, font));
            if(!isbold){
                font.setStyle(defaultStyle);
            }
        }
    }
    protected void setCellBorder(PdfPCell cell, CellSettings settings){
        float [] borders = PDFUtil.getBorders(settings.getBorder());
        Color bordercolor = PDFUtil.getRGBColor(settings.getBordercolorrgb(), PDF.DEFAULT_BORDER_COLOR);

        cell.setBorder(0);
        if(borders[0] > 0){
            cell.setBorderWidthLeft(borders[0]);
            cell.setBorderColorLeft(bordercolor);
        }
        if(borders[1] > 0){
            cell.setBorderWidthTop(borders[1]);
            cell.setBorderColorTop(bordercolor);
        }
        if(borders[2] > 0){
            cell.setBorderWidthRight(borders[2]);
            cell.setBorderColorRight(bordercolor);
        }
        if(borders[3] > 0){
            cell.setBorderWidthBottom(borders[3]);
            cell.setBorderColorBottom(bordercolor);
        }
    }
    protected void setCellPadding(PdfPCell cell, CellSettings settings){
        float [] paddings = PDFUtil.getPaddings(settings.getPadding());
        cell.setPadding(0);
        if(paddings[0] > 0){
            cell.setPaddingLeft(paddings[0]);
        }
        if(paddings[1] > 0){
            cell.setPaddingTop(paddings[1]);
        }
        if(paddings[2] > 0){
            cell.setPaddingRight(paddings[2]);
        }
        if(paddings[3] > 0){
            cell.setPaddingBottom(paddings[3]);
        }
    }
}
