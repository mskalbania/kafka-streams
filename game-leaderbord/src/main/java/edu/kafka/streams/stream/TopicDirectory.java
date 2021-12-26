package edu.kafka.streams.stream;

import edu.kafka.streams.model.Player;
import edu.kafka.streams.model.Product;
import edu.kafka.streams.model.ScoreEvent;
import edu.kafka.streams.util.JsonSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class TopicDirectory {

    public static KStream<Void, ScoreEvent> scoreEvents(StreamsBuilder streamsBuilder) {
        return streamsBuilder.stream("scores", Consumed.with(Serdes.Void(), JsonSerde.forType(ScoreEvent.class)));
    }

    public static KTable<String, Player> players(StreamsBuilder streamsBuilder) {
        return streamsBuilder.table("players", Consumed.with(Serdes.String(), JsonSerde.forType(Player.class)));
    }

    public static GlobalKTable<String, Product> products(StreamsBuilder streamsBuilder) {
        return streamsBuilder.globalTable("products", Consumed.with(Serdes.String(), JsonSerde.forType(Product.class)));
    }
}
