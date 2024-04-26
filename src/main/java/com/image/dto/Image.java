package com.image.dto;

import com.image.enums.ImageStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Image {
    private Integer id;
    private String objectPath;
    private String objectSize;
    private LocalDate timeAdded;
    private LocalDate timeUpdated;
    private List<String> labels;
    private ImageStatus status;

    public void init(List args) {
    }
}
