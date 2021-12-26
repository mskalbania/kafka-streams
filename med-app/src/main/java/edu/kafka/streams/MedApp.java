package edu.kafka.streams;

import edu.kafka.streams.stream.MedAppSpecification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class MedApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MedApp.class, args);
    }

    private static final String HB_TOPIC = "heartbeats";
    private static final String TEMP_TOPIC = "temperature";
    private final MedAppSpecification medAppSpecification = new MedAppSpecification();

    @Override
    public void run(String... args) {
        Topology topology = medAppSpecification.create();
        System.out.println(topology.describe());

        KafkaStreams kafkaStreams = new KafkaStreams(topology, getConfig());
        kafkaStreams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));


//        new ScheduledProducer().run(HB_TOPIC, 2, 130, 60, "1", "{\"timestamp\":\"\"}");
//        new ScheduledProducer().run(TEMP_TOPIC, 2, 1, 20, "1", "{\"timestamp\": \"\", \"value\": 42.0}");
    }

    private static Properties getConfig() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "dev1");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        config.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);
        config.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
        config.put(StreamsConfig.STATE_DIR_CONFIG, "/Users/mskalbania/Documents/GIT/kafka-streams/state");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        config.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, "org.apache.kafka.streams.processor.LogAndSkipOnInvalidTimestamp");
        return config;
    }
}
