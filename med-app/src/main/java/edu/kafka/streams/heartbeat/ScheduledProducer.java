package edu.kafka.streams.heartbeat;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ScheduledProducer {


    private final Producer<String, String> producer = new KafkaProducer<>(config());
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public void run(String topic, long delay, int amount, int window, String key, String value) {
        scheduledExecutorService.scheduleAtFixedRate(() -> IntStream.range(0, amount)
                .forEach(__ -> producer.send(new ProducerRecord<>(topic, key, value))), delay, window, TimeUnit.SECONDS);
    }

    private Properties config() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9093");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return properties;
    }
}
