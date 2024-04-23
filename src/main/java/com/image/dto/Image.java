package com.image.dto;

import com.image.enums.ImageStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class Image {
  private Integer id;
  private String objectPath;
  private String objectSize;
  private LocalDate timeAdded;
  private LocalDate timeUpdated;
  private List<String> labels;
  private List<ImageStatus> status;

  public void init(List args) {}
}
