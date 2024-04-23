package com.image.client;

import com.image.consts.DynamoDbConst;
import com.image.dto.Image;
import com.image.repository.ImageTable;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class ImageDataBaseClient {

  @Autowired DynamoDbEnhancedClient dynamoDbEnhancedClient;

  public void init() {}

  public Integer create(Image image) {
    DynamoDbTable<ImageTable> imageTableTable =
        dynamoDbEnhancedClient.table(
            DynamoDbConst.IMAGE_TABLE, TableSchema.fromBean(ImageTable.class));
    Integer id = Integer.valueOf(UUID.randomUUID().toString());

    ImageTable imageTable = new ImageTable();
    imageTable.setId(id);

    imageTableTable.putItem(imageTable);
    return id;
  }

  public Image read(Integer id) {
    return null;
  }

  public void delete(Integer id) {}

  public void search(Integer id) {}
}
