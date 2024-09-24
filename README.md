# AddKafkaHeadersToValueTransformation

**AddKafkaHeadersToValueTransformation** is a custom Single Message Transform (SMT) that enhances messages consumed from Kafka by injecting additional metadata into the message payload. This transform captures key Kafka-related information such as:

Topic: The topic from which the message was consumed.
Partition: The partition of the topic.
Timestamp: The time when the message was produced.
This metadata is added to the value of the message under a **kafka_headers** field, making it easier to trace the origin of messages and to use this information in downstream systems for logging, debugging, or processing purposes.

Example of how the added metadata looks in the message value:



```json
{
  "data": {
    // original message data
  },
  "kafka_headers": {
    "topic": "my_topic",
    "partition": 0,
    "timestamp": 1632425623000
  }
}

```

This SMT is useful in cases where you need to retain Kafka message metadata for tracking or operational insights without modifying the original message structure.

Additionally, you can customize the name of the field where the Kafka metadata is stored using the **field.name** configuration in your Kafka connector. By default, the metadata will be added under the **kafka_headers** field, but you can specify a custom field name to suit your requirements.

Example configuration in the Kafka connector:

```
"transforms": "AddHeadersToValueTransformation"
"transforms.AddKafkaHeadersToValueTransformation.type": "com.digikala.AddKafkaHeadersToValueTransformation",
"transforms.AddKafkaHeadersToValueTransformation.field.name": "kafka_headers"

```

# Build
To build the project and start using the transformation, simply run the following command in the root directory of the project:

``` 
mvn clean install

```

This will compile the code, run any tests, and package the transformation into a JAR file that you can then include in your Kafka Connect setup. Once the build completes successfully, you can use the generated JAR file in your Kafka connector's classpath to enable the AddKafkaHeadersToValueTransformation in your Kafka Connect configuration.

