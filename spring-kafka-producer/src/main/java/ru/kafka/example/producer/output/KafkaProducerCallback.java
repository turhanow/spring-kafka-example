package ru.kafka.example.producer.output;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

@Slf4j
public record KafkaProducerCallback() implements Callback {

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (Objects.isNull(e)) {
            log.info("Data successfully sent to the topic -> {}", recordMetadata.topic());
        } else {
            log.error("Data unsuccessfully sent to the topic -> {}, reason: ", recordMetadata.topic(), e);
        }
    }
}