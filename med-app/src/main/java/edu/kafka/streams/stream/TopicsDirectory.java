package edu.kafka.streams.stream;

import edu.kafka.streams.model.HeartBeat;
import edu.kafka.streams.model.Temperature;
import edu.kafka.streams.util.JsonSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

public class TopicsDirectory {

    public static KStream<String, HeartBeat> heartBeats(StreamsBuilder streamsBuilder) {
        Consumed<String, HeartBeat> heartbeatsConsumed = Consumed.with(Serdes.String(), JsonSerde.forType(HeartBeat.class))
                .withTimestampExtractor(new CustomTimeStampExtractor());
        return streamsBuilder.stream("heartbeats", heartbeatsConsumed);
    }

    public static KStream<String, Temperature> temperature(StreamsBuilder streamsBuilder) {
        Consumed<String, Temperature> temperatureConsumed = Consumed.with(Serdes.String(), JsonSerde.forType(Temperature.class))
                .withTimestampExtractor(new CustomTimeStampExtractor());
        return streamsBuilder.stream("temperature", temperatureConsumed);
    }
}
