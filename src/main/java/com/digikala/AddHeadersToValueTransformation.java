package com.digikala;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.transforms.Transformation;

import java.util.HashMap;
import java.util.Map;

public class AddHeadersToValueTransformation <R extends ConnectRecord<R>> implements Transformation<R> {

    private String fieldName;
    @Override
    public R apply(R record) {


        // Create a new map for the value if it's not already a map
        Map<String, Object> valueMap;
        if (record.value() == null) {
            valueMap = new HashMap<>();
        } else {
            valueMap = (Map<String, Object>) record.value();
        }
        Map<String , Object> kafkaHeader=new HashMap<>();;
        kafkaHeader.put("topic",record.topic());
        kafkaHeader.put("timestamp",record.timestamp());
        kafkaHeader.put("partition",record.kafkaPartition());
        kafkaHeader.put("key",record.key());



        // Add topic and timestamp , ... to the value map
        valueMap.put(fieldName,kafkaHeader);

        // Create a new record with the updated value
        return record.newRecord(
                record.topic(),
                record.kafkaPartition(),
                record.keySchema(),
                record.key(),
                record.valueSchema(),
                valueMap,
                record.timestamp()
        );
    }

    @Override
    public void configure(Map<String, ?> configs) {
        Object fieldNameObj = configs.get("field.name");
        fieldName = (fieldNameObj != null) ? fieldNameObj.toString() : "kafka_headers";
    }

    @Override
    public void close() {
        // Clean up resources if needed
    }

    @Override
    public ConfigDef config() {
        return new ConfigDef()
                .define("field.name", ConfigDef.Type.STRING, "kafka_headers", ConfigDef.Importance.HIGH, "The field name to add to the value map");
    }
}
