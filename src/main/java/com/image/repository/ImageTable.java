package com.image.repository;

import static com.image.consts.DynamoDbConst.IMAGE_TABLE;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.image.enums.ImageStatus;
import java.util.List;
import lombok.Data;

@DynamoDBTable(tableName = IMAGE_TABLE)
@Data
public class ImageTable {

  @DynamoDBHashKey private Integer id;
  @DynamoDBAttribute private String objectPath;
  @DynamoDBAttribute private String objectSize;
  @DynamoDBAttribute private String timeAdded;
  @DynamoDBAttribute private String timeUpdated;
  @DynamoDBAttribute private List<String> labels;

  @DynamoDBAttribute private ImageStatus imageStatus;
}
