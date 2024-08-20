package ru.kafka.example.producer.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kafka.example.producer.properties.KafkaProducerProperties;

@Configuration
@EnableConfigurationProperties(KafkaProducerProperties.class)
public class KafkaConfig {

    @Bean
    public KafkaProducer<String, byte[]> kafkaProducer(KafkaProducerProperties kafkaProducerProperties) {
        var producerProperties = kafkaProducerProperties.getProducer().buildProperties(null);
        return new KafkaProducer<>(producerProperties);
    }
}