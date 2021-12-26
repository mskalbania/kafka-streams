package edu.kafka.streams;

import edu.kafka.streams.stream.GameLeaderboardSpecification;
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
public class GameLeaderboard implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GameLeaderboard.class, args);
    }

    private final GameLeaderboardSpecification gameLeaderboardSpecification = new GameLeaderboardSpecification();

    @Override
    public void run(String... args) {
        Topology topology = gameLeaderboardSpecification.create();
        System.out.println(topology.describe());

        KafkaStreams kafkaStreams = new KafkaStreams(topology, getConfig());
        kafkaStreams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }

    private static Properties getConfig() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "dev1");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        config.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 3);
        config.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
        config.put(StreamsConfig.STATE_DIR_CONFIG, "/Users/mskalbania/Documents/GIT/kafka-streams/state");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return config;
    }
}
