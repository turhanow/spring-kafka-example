package ru.kafka.example.producer.output;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kafka.example.producer.service.GenericRecordConverter;

@Component
@RequiredArgsConstructor
public class DataProducer {

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    private final GenericRecordConverter genericRecordConverter;
    private final KafkaProducer<String, byte[]> kafkaProducer;

    public void send(GenericRecord record) {
        var body = genericRecordConverter.toByteArray(record);
        var producerRecord = new ProducerRecord<String, byte[]>(topic, null, body);
        kafkaProducer.send(producerRecord, new KafkaProducerCallback());
    }

    @PreDestroy
    public void preDestroy() {
        kafkaProducer.close();
    }
}