package ru.kafka.example.producer.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kafka.example.producer.output.DataProducer;

@Service
@RequiredArgsConstructor
public class DataProducerScheduler {

    private final DataProducer dataProducer;

    @Scheduled(fixedRateString = "${scheduler.data-producer}")
    void produce() {
        var message = UUID.randomUUID();
        dataProducer.send(message);
    }
}