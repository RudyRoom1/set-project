package com.image.utils;

import com.image.dto.Image;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;

import java.time.LocalDate;
import java.util.Collections;

import static com.image.enums.ImageStatus.NEW;

@Component
public class ImageGenerator {
    public Image imageGenerator(MultipartFile file) {

        return Image.builder()
                .id(GeneratorUtils.generateId())
                .objectPath(GeneratorUtils.generateRandomFilePath(file))
                .objectSize(String.valueOf(file.getSize()))
                .timeAdded(LocalDate.now())
                .timeUpdated(LocalDate.now())
                .status(NEW)
                .labels(Collections.singletonList("defaultLabel"))
                .build();
    }
}
