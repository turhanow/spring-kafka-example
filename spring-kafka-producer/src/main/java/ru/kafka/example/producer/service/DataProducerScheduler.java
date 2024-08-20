package ru.kafka.example.producer.service;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kafka.example.producer.output.DataProducer;

@Service
@RequiredArgsConstructor
public class DataProducerScheduler {

    private final DataProducer dataProducer;

    @Scheduled(fixedRateString = "${scheduler.data-producer}")
    void produce() {
        var user = avro.User.newBuilder()
            .setName(RandomStringUtils.randomAlphabetic(10))
            .setAge(new Random().nextInt(100))
            .build();
        dataProducer.send(user);
    }
}