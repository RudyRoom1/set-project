package com.image.repository;

import com.image.dto.Image;
import com.image.enums.ImageStatus;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.image.consts.DateFormatter.FORMATTER;

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

    public Image convertToImage(Map<String, AttributeValue> item) {

        List<String> labels = item.get("Labels").ss();
        ImageStatus status = ImageStatus.valueOf(item.get("Status").s());

        return Image.builder()
                .id(Integer.parseInt(item.get("Id").n()))
                .objectPath(item.get("ObjectPath").s())
                .objectSize(item.get("ObjectSize").n())
                .timeAdded(LocalDate.parse(item.get("TimeAdded").s(), FORMATTER))
                .timeUpdated(LocalDate.parse(item.get("TimeUpdated").s(), FORMATTER))
                .labels(labels)
                .status(status)
                .build();
    }
}
