package com.image.repository;

import com.image.dto.Image;
import com.image.enums.ImageStatus;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DbConvertor {

    public Map<String, AttributeValue> createDbImage(Image image) {
        Map<String, AttributeValue> item = new HashMap<>();
        String name = image.getObjectPath().substring(image.getObjectPath().lastIndexOf("/") + 1);

        item.put("LabelValue", AttributeValue.builder().s(name).build());
        item.put("ImageName", AttributeValue.builder().s(name).build());
        if (image.getId() != null)
            item.put("id", AttributeValue.builder().n(String.valueOf(image.getId())).build());

        if (image.getObjectPath() != null)
            item.put("objectPath", AttributeValue.builder().s(image.getObjectPath()).build());

        if (image.getObjectSize() != null)
            item.put("objectSize", AttributeValue.builder().s(image.getObjectSize()).build());

        if (image.getTimeAdded() != null)
            item.put("timeAdded", AttributeValue.builder().s(image.getTimeAdded().toString()).build());

        if (image.getTimeUpdated() != null)
            item.put("timeUpdated", AttributeValue.builder().s(image.getTimeUpdated().toString()).build());

        if (image.getLabels() != null)
            item.put("labels", AttributeValue.builder().ss(image.getLabels()).build());

        if (image.getStatus() != null)
            item.put("imageStatus", AttributeValue.builder().s(image.getStatus().toString()).build());


        return item;
    }

    public Image fromDynamoDBItem(Map<String, AttributeValue> item) {
        List<String> labels = item.get("labels").l().stream()
                .map(AttributeValue::s)
                .toList();
        return Image.builder()
                .id(Integer.parseInt(item.get("id").n()))
                .objectPath(item.get("objectPath").s())
                .objectSize(item.get("objectSize").s())
                .timeAdded(LocalDate.parse(item.get("timeAdded").s(), DateTimeFormatter.ISO_DATE))
                .timeUpdated(LocalDate.parse(item.get("timeUpdated").s(), DateTimeFormatter.ISO_DATE))
                .labels(labels)
                .status(ImageStatus.valueOf(item.get("imageStatus").s()))
                .LabelValue(item.get("LabelValue").s())
                .ImageName(item.get("ImageName").s())
                .build();
    }
}
