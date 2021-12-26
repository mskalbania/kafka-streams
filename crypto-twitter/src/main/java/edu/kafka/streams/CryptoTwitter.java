package edu.kafka.streams;

import edu.kafka.streams.service.SentimentAnalysisService;
import edu.kafka.streams.service.TranslationService;
import edu.kafka.streams.stream.CryptoTwitterSpecification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class CryptoTwitter implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CryptoTwitter.class, args);
    }

    private final TranslationService translationService = new TranslationService();
    private final SentimentAnalysisService sentimentAnalysisService = new SentimentAnalysisService();
    private final CryptoTwitterSpecification cryptoTwitterSpecification = new CryptoTwitterSpecification(translationService, sentimentAnalysisService);

    @Override
    public void run(String... args) {

        KafkaStreams kafkaStreams = new KafkaStreams(cryptoTwitterSpecification.create(), getConfig());
        kafkaStreams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }

    private static Properties getConfig() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "dev1");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        config.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return config;
    }
}
