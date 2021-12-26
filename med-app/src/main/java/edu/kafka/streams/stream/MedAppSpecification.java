package edu.kafka.streams.stream;

import edu.kafka.streams.model.HeartBeat;
import edu.kafka.streams.model.Temperature;
import edu.kafka.streams.util.JsonSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.StreamJoined;

import java.time.Duration;

@Slf4j
public class MedAppSpecification {

    public Topology create() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String, HeartBeat> heartbeatsStream = TopicsDirectory.heartBeats(streamsBuilder);
        KStream<String, Temperature> temperatureStream = TopicsDirectory.temperature(streamsBuilder);
//                .filter((key, value) -> value.getValue() > 39.0);
//
//        KStream<String, Long> heartRates = heartbeatsStream.groupByKey()
//                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(60)))
//                .count(Materialized.as("bpm-events"))
//                .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded().shutDownWhenFull()))
//                .toStream()
//                .filter((windowedId, value) -> value > 110)
//                .map((windowedId, value) -> KeyValue.pair(windowedId.key(), value));
//
//        StreamJoined<String, Long, Temperature> ratesTemperaturesJoined = StreamJoined.with(Serdes.String(), Serdes.Long(), JsonSerde.forType(Temperature.class));
//        JoinWindows lookupWindow = JoinWindows.of(Duration.ofSeconds(60)).grace(Duration.ofSeconds(1));//1-minute window lookup
//
//
//        heartRates.join(temperatureStream, HealthEndangered::new, lookupWindow, ratesTemperaturesJoined)
//                .foreach((key, value) -> log.info("Patient {}, endangered - {}", key, value));

        heartbeatsStream.join(temperatureStream, (heartBeat, temperature) -> "joined", JoinWindows.of(Duration.ofSeconds(60)), StreamJoined.with(Serdes.String(), JsonSerde.forType(HeartBeat.class), JsonSerde.forType(Temperature.class)))
                .foreach((k, v) -> System.out.println(v));

        return streamsBuilder.build();
    }
}
