package ru.kafka.example.consumer.input;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvoKafkaConsumer implements Runnable {

    private final KafkaConsumer<String, byte[]> kafkaConsumer;
    private final AtomicBoolean shutdown = new AtomicBoolean(false);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Value("${kafka.input.topic}")
    private String topic;

    @Override
    public void run() {
        while (!shutdown.get()) {
            var records = kafkaConsumer.poll(Duration.ofMillis(100));
            records.forEach(this::doProcess);
            // Асинхронный коммит смещений, фиксируем после обработки данных
            kafkaConsumer.commitAsync((offsets, exception) -> {
                if (Objects.nonNull(exception)) {
                    log.error("Commit failed for offsets: {} due to {}", offsets, exception.getLocalizedMessage());
                }
            });
        }
    }

    private void doProcess(ConsumerRecord<String, byte[]> record) {
        try {
            // Декодируем запись из Avro формата
            var reader = new SpecificDatumReader<>(avro.User.class);
            var decoder = DecoderFactory.get().binaryDecoder(record.value(), null);
            var user = reader.read(null, decoder);
            System.out.printf("Received user: %s%n", user);
        } catch (Exception ex) {
            log.error("Unexpected error:", ex);
            // Возвращаемся на текущее смещение
            kafkaConsumer.seek(new TopicPartition(topic, record.partition()), record.offset());
        }
    }

    @PostConstruct
    private void init() {
        // Подписываемся на Kafka топик и запускаем обработку сообщений в отдельном потоке
        kafkaConsumer.subscribe(List.of(topic));
        executorService.submit(this);
    }

    @PreDestroy
    public void onShutdown() {
        // Устанавливаем флаг остановки, чтобы выйти из цикла обработки сообщений и закрываем kafkaConsumer
        shutdown.set(true);
        kafkaConsumer.close();
        // Пытаемся корректно завершить работу ExecutorService
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                log.error("Executor did not terminate in the specified time.");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}