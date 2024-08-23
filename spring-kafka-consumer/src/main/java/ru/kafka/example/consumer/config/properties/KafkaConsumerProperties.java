package ru.kafka.example.consumer.config.properties;

import lombok.Getter;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaConsumerProperties {

    private final KafkaProperties.Consumer consumer = new KafkaProperties.Consumer();
}
