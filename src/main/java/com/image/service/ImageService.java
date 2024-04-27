package com.image.service;

import com.image.client.ImageDataBaseClient;
import com.image.client.ImageStorageClient;
import com.image.dto.Image;
import com.image.utils.ImageGenerator;
import lombok.Data;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Data
public class ImageService {
    public static final String SUCCESS = "File uploaded successfully to s3 %s and to DynamoDb table %s with id: %s";
    @Autowired
    ImageStorageClient storageClient;
    @Autowired
    ImageDataBaseClient dbClient;
    @Autowired
    ImageGenerator imageGenerator;
    @Value("${dynamoDb.tableName}")
    String tableName;
    @Value("${s3.bucketName}")
    String bucketName;

    public void init() {
    }

    public ResponseEntity<String> create(MultipartFile file) {
        try {
            storageClient.create(file);
            Image dynamoRecord = imageGenerator.imageGenerator(file);

            Integer recordId = dbClient.create(dynamoRecord);
            return new ResponseEntity<>(String.format(SUCCESS, bucketName, tableName, recordId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Response get(Integer id) {
        return null;
    }

    public ResponseEntity<List<Image>> getFilesByLabel(String label) {

        return new ResponseEntity<>(dbClient.readByLabel(label), HttpStatus.OK);
    }

    public Response delete(Integer id) {
        return null;
    }

    public Response search(Integer id) {
        return null;
    }
}
