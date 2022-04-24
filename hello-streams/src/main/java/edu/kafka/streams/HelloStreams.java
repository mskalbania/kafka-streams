package edu.kafka.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.apache.kafka.streams.processor.StateStore;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.apache.kafka.streams.state.internals.InMemoryKeyValueStore;
import org.apache.kafka.streams.state.internals.InMemoryWindowBytesStoreSupplier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;

import static java.time.temporal.ChronoUnit.MINUTES;

@SpringBootApplication
public class HelloStreams implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HelloStreams.class, args);
    }

    @Override
    public void run(String... args) {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        StreamJoined<String, String, String> stores = StreamJoined.with(Serdes.String(), Serdes.String(), Serdes.String())
                .withThisStoreSupplier(Stores.inMemoryWindowStore("device", Duration.ofMinutes(10), Duration.ofMinutes(10), true))
                .withOtherStoreSupplier(Stores.inMemoryWindowStore("authentication", Duration.ofMinutes(10), Duration.ofMinutes(10), true));

        StreamJoined<String, String, String> stores2 = StreamJoined.with(Serdes.String(), Serdes.String(), Serdes.String())
                .withThisStoreSupplier(Stores.inMemoryWindowStore("base", Duration.ofMinutes(2), Duration.ofMinutes(2), true))
                .withOtherStoreSupplier(Stores.inMemoryWindowStore("already", Duration.ofMinutes(2), Duration.ofMinutes(2), true));

        KStream<String, String> deviceLoginBySession = streamsBuilder.stream(List.of("device-login"));
        KStream<String, String> authResultBySession = streamsBuilder.stream(List.of("authentication-result"));
        KStream<String, String> notificationBySession = streamsBuilder.stream(List.of("notify"));

//        deviceLoginBySession.print(Printed.toSysOut());
//        authResultBySession.print(Printed.toSysOut());

        notificationBySession.print(Printed.toSysOut());

//        Both trigger lookup events from both sides are matched to each other
        deviceLoginBySession.join(
                        authResultBySession,
                        (device, result) -> device + ":" + result,
                        JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(5)),
                        stores)
                .map((k, v) -> new KeyValue<>(v, v))
                .leftJoin(
                        notificationBySession,
                        (base, notificationSent) -> notificationSent == null ? base : null,
                        JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(1)),
                        stores2)
                .to("notify");


        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "dev1");
        config.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, "2");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        Topology topology = streamsBuilder.build();
        System.out.printf(topology.describe().toString());

        KafkaStreams streams = new KafkaStreams(topology, config);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
