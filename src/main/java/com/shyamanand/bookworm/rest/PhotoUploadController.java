package com.shyamanand.bookworm.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PhotoUploadController {

    @GetMapping("/upload")
    public String uploadForm() {
        return "uploadForm";
    }
}
