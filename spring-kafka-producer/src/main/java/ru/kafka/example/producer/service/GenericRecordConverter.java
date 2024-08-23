package ru.kafka.example.producer.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GenericRecordConverter {

    public byte[] toByteArray(@NonNull GenericRecord genericRecord) {
        try (var out = new ByteArrayOutputStream()) {
            var writer = new SpecificDatumWriter<>(genericRecord.getSchema());
            var encoder = EncoderFactory.get().binaryEncoder(out, null);
            writer.write(genericRecord, encoder);
            encoder.flush();

            return out.toByteArray();
        } catch (IOException ex) {
            log.error("Something went wrong while converting avro record", ex);
            throw new IllegalArgumentException("Cannot convert avro record", ex);
        }
    }
}