package com.image.controller;

import com.image.dto.Image;
import com.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/images")
public class ImageController {

    @Autowired
    ImageService imageService;


    @Operation(summary = "Get image files data by label \n")
    @GetMapping("/getImageFiles")
    public ResponseEntity<List<Image>> getImagesByLabel(@RequestParam("label") String label) {
        return imageService.getFilesByLabel(label);
    }

    @Operation(summary = "Upload image file to S3 bucket \n")
    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        return imageService.create(file);
    }

    @DeleteMapping("/image/{id}")
    public String deleteImage(@PathVariable("id") Long imageId) {
        return "deleted";
    }
}
