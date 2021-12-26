package edu.kafka.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class HelloStreams implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HelloStreams.class, args);
	}

	@Override
	public void run(String... args) {
		StreamsBuilder streamsBuilder = new StreamsBuilder();

		KStream<Void, String> usersStream = streamsBuilder.stream(List.of("users", "admins"));
		usersStream.foreach((key, value) -> System.out.println(value));

		Properties config = new Properties();
		config.put(StreamsConfig.APPLICATION_ID_CONFIG, "dev1");
		config.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, "2");
		config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Void().getClass());
		config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), config);
		streams.start();

		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
	}
}
