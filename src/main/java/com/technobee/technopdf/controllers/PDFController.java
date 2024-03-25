package com.technobee.technopdf.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.technobee.technopdf.dtos.PDFRequest;
import com.technobee.technopdf.services.PDFGenerator;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("pdf")
public class PDFController {

    @Autowired
    PDFGenerator pdfService;

    @GetMapping("request-metadata")
    public PDFRequest getRequestMetaData(){
        return new PDFRequest();
    }

    @PostMapping("generate")
    public ResponseEntity<byte[]> getPdf(@RequestBody PDFRequest request) throws IOException {
        byte[] pdfBytes = pdfService.pdfReport(request);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "report.pdf");
        headers.setContentLength(pdfBytes.length);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
