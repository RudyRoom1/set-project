package com.image.client;

import com.image.dto.Image;
import com.image.repository.DbConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImageDataBaseClient {

    @Value("${dynamoDb.tableName}")
    String tableName;

    @Autowired
    DbConvertor converter;

    @Autowired
    DbConvertor dbConvertor;

    @Autowired
    DynamoDbClient dynamoDbClient;

    public void init() {
    }

    public Integer create(Image image) {
        Map<String, AttributeValue> dbImage = dbConvertor.createDbImage(image);

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(dbImage)
                .build();

        PutItemResponse putItemResponse = dynamoDbClient.putItem(request);
        return image.getId();
    }

    public Image read(Integer id) {
        return null;
    }

    public List<Image> getByLabel(String label) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":labelVal", AttributeValue.builder().s(label).build());

        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tableName)
                .filterExpression("contains(labels, :labelVal)")
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        ScanResponse response = dynamoDbClient.scan(scanRequest);

        return response.items().stream().map(converter::fromDynamoDBItem).collect(Collectors.toList());
    }

    public void delete(Integer id) {
    }

    public void search(Integer id) {
    }
}
