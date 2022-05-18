package com.example.boardPrac2.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;


@RestController
public class FileController {

    @GetMapping("/video")
    public ResponseEntity<InputStreamResource> retrieveResource() throws Exception {
        File file = new File("C:/spring/temp/a3pO9IXyfKFo2t0e2xkRzb4y1tohh119.pptx");
//        "C:/spring/temp/a3pO9IXyfKFo2t0e2xkRzb4y1tohh119.pptx"
        FileInputStream fileInputStream = new FileInputStream("C:/spring/temp/a3pO9IXyfKFo2t0e2xkRzb4y1tohh119.pptx");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Ranges", "bytes");
        headers.set("Content-Type", "video/mp4");
        headers.set("Content-Range", "bytes 50-1025/17839845");
        headers.set("Content-Length", String.valueOf(file.length()));
        return new ResponseEntity<>(new InputStreamResource(fileInputStream), headers, HttpStatus.OK);
    	}

	}
