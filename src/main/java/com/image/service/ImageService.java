package com.image.service;

import com.image.client.ImageDataBaseClient;
import com.image.client.ImageStorageClient;
import java.io.File;
import lombok.Data;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;

@Service
@Data
public class ImageService {
  private ImageStorageClient storageClient;
  private ImageDataBaseClient dbClient;

  public void init() {}

  public Response create(File file) {
    return null;
  }

  public Response get(Integer id) {
    return null;
  }

  public Response delete(Integer id) {
    return null;
  }

  public Response search(Integer id) {
    return null;
  }
}
