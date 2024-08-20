package ru.kafka.example.producer.output;

import jakarta.annotation.PreDestroy;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

@Component
@RequiredArgsConstructor
public class DataProducer {

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    private final KafkaProducer<String, byte[]> kafkaProducer;

    public void send(Serializable data) {
        var body = SerializationUtils.serialize(data);
        var producerRecord = new ProducerRecord<String, byte[]>(topic, null, body);
        kafkaProducer.send(producerRecord, new KafkaProducerCallback());
    }

    @PreDestroy
    public void preDestroy() {
        kafkaProducer.close();
    }
}