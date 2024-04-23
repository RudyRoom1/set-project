package com.image.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RestController
@Slf4j
@RequestMapping("/images")
public class ImageController {
  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();
  private final DynamoDbClient dynamodb;
  private final RekognitionClient rekognition;
  private final S3Client s3;

  @Autowired
  public ImageController(S3Client s3, DynamoDbClient dynamodb, RekognitionClient rekognition) {
    this.s3 = s3;
    this.dynamodb = dynamodb;
    this.rekognition = rekognition;
  }

  @Operation(summary = "Get image files data by label \n")
  @GetMapping("/getImageFiles")
  public ResponseEntity<List<String>> getImagesByLabel(@RequestParam("label") String label) {
    // In this method, you would query DynamoDB for all images associated with the input label,
    // and return those images. Here is a basic idea of how you might handle it:

    // Query DynamoDB for images associated with this label
    QueryResponse query =
        dynamodb.query(
            QueryRequest.builder()
                .tableName("labels-table")
                .keyConditionExpression("label = :label")
                .expressionAttributeValues(
                    Collections.singletonMap(":label", AttributeValue.builder().s(label).build()))
                .build());

    // Extract the image names from the response
    List<String> imageNames =
        query.items().stream().map(item -> item.get("imageName").s()).collect(Collectors.toList());

    return new ResponseEntity<>(imageNames, HttpStatus.OK);
  }

  @Operation(summary = "Upload image file to S3 bucket \n")
  @PostMapping("/image")
  public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
    try {
      PutObjectRequest objectRequest =
          PutObjectRequest.builder().bucket("bucket-name").key(file.getOriginalFilename()).build();

      s3.putObject(
          objectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

      return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/image/{id}")
  public String deleteImage(@PathVariable("id") Long imageId) {
    return "deleted";
  }
}
