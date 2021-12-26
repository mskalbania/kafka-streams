package edu.kafka.streams.stream;

import edu.kafka.streams.model.*;
import edu.kafka.streams.util.JsonSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;

public class GameLeaderboardSpecification {

    public Topology create() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<Void, ScoreEvent> scoreEvents = TopicDirectory.scoreEvents(streamsBuilder);
        KTable<String, Player> players = TopicDirectory.players(streamsBuilder);
        GlobalKTable<String, Product> products = TopicDirectory.products(streamsBuilder);

        KeyValueMapper<String, PlayerWithScore, String> withProductIdMapper = (playerId, playerWithScore) ->
                String.valueOf(playerWithScore.getScoreEvent().getProductId());

        //TOPOLOGY 0 -> marked for repartition  TASKS 0_*
        scoreEvents.selectKey((__, player) -> player.getPlayerId().toString())

                //TOPOLOGY 1 TASKS 1_*
                .join(players, (score, player) -> new PlayerWithScore(player, score), Joined.with(Serdes.String(), JsonSerde.forType(ScoreEvent.class), JsonSerde.forType(Player.class)))

                //TOPOLOGY 2 no tasks -> global store
                .join(products, withProductIdMapper, Enriched::new)

                //TOPOLOGY 3 -> marked for repartition TASKS 3_*
                .groupBy((playerId, enriched) -> String.valueOf(enriched.getProductId()), Grouped.with(Serdes.String(), JsonSerde.forType(Enriched.class)))
                .aggregate(HighScores::new, (productId, enriched, highScores) -> highScores.add(enriched),
                        Materialized.<String, HighScores, KeyValueStore<Bytes, byte[]>>as("leaderboard")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(JsonSerde.forType(HighScores.class)));

        //OR AS STREAM OF UPDATES TO HIGH SCORE topic
        //.toStream().to("high-scores", Produced.with(Serdes.String(), JsonSerde.forType(HighScores.class)));
        return streamsBuilder.build();
    }
}
