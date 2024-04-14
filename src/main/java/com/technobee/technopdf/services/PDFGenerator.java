package com.technobee.technopdf.services;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.technobee.technopdf.dtos.CellData;
import com.technobee.technopdf.dtos.PDFRequest;
import com.technobee.technopdf.dtos.Page;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PDFGenerator extends PDFDocument {

    public byte[] pdfReport(PDFRequest request){
        ByteArrayOutputStream outputStream = null;
        try {
            initializeDocument(request.getPagesettings());
            outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            int pageno = 1;
            for (Page page : request.getPages()) {
                if(pageno > 1){
                    document.newPage();
                }
                initializeTable(page.getTable());
                for (CellData celldata : page.getContents()) {
                    appendCell(celldata);
                }
                document.add(table);
                pageno ++;
            }
            document.close();
            byte[] pdfBytes = outputStream.toByteArray();
            return pdfBytes;
        } catch (DocumentException e) {
            log.error("Exception while generating pdfReport(): {}", e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                log.error("Exception while closing outputStream pdfReport(): {}", e);
            }
        }
        return null; 
    }
}
