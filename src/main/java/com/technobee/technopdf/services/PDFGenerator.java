package com.technobee.technopdf.services;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.technobee.technopdf.dtos.CellData;
import com.technobee.technopdf.dtos.PDFRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PDFGenerator extends PDFDocument {

    public byte[] pdfReport(PDFRequest request){
        ByteArrayOutputStream outputStream = null;
        try {
            initializeDocument(request.getPage());
            outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            initializeTable(request.getTable());
            for (CellData celldata : request.getContents()) {
                appendCell(celldata);
            }
            document.add(table);
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
