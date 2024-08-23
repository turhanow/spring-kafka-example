package ru.kafka.example.consumer.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kafka.example.consumer.config.properties.KafkaConsumerProperties;

@Configuration
@EnableConfigurationProperties(KafkaConsumerProperties.class)
public class KafkaConfig {

    @Bean
    public KafkaConsumer<String, byte[]> kafkaConsumer(KafkaConsumerProperties kafkaConsumerProperties) {
        var consumerProperties = kafkaConsumerProperties.getConsumer().buildProperties(null);
        return new KafkaConsumer<>(consumerProperties);
    }
}