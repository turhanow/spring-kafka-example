package ru.kafka.example.producer.properties;

import lombok.Getter;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProducerProperties {

    private final KafkaProperties.Producer producer = new KafkaProperties.Producer();
}
