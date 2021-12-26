package edu.kafka.streams.stream;

import edu.kafka.streams.model.Vital;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.Instant;

//Tries to extract timestamp from event payload or defaults to metadata timestamp
public class CustomTimeStampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        try {
            Vital vital = (Vital) record.value();
            String timeStamp = vital.getTimeStamp();
            return Instant.parse(timeStamp).toEpochMilli();
        } catch (Exception e) {
            return record.timestamp();
        }
    }
}
