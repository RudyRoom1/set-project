package com.image.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

public class ImageRekognitionLambda implements RequestHandler<S3Event, Void> {

  public static final String IMAGE_TABLE = "ImageTable";
  private RekognitionClient rekognition = RekognitionClient.create();
  private DynamoDbClient dynamodb = DynamoDbClient.create();

  public Void handleRequest(S3Event s3event, Context context) {
    s3event
        .getRecords()
        .forEach(
            record -> {
              // Get the name of the bucket and key.
              String bucket = record.getS3().getBucket().getName();
              String key = record.getS3().getObject().getUrlDecodedKey();

              try {
                // Call Amazon Rekognition to detect labels in the image.
                DetectLabelsResponse result =
                    rekognition.detectLabels(
                        DetectLabelsRequest.builder()
                            .image(
                                Image.builder()
                                    .s3Object(S3Object.builder().bucket(bucket).name(key).build())
                                    .build())
                            .build());

                // Save the labels in Amazon DynamoDB.
                result.labels().stream()
                    .map(Label::name)
                    .forEach(
                        label -> {
                          Map<String, AttributeValue> item = new HashMap<>();
                          item.put("LabelValue", AttributeValue.builder().s(label).build());
                          item.put("ImageName", AttributeValue.builder().s(key).build());

                          PutItemRequest putItemRequest =
                              PutItemRequest.builder().tableName(IMAGE_TABLE).item(item).build();

                          dynamodb.putItem(putItemRequest);
                        });
              } catch (Exception e) {
                System.out.println(
                    "Error processing object " + key + " from bucket " + bucket + ".");
                e.printStackTrace();
              }
            });

    return null;
  }
}
