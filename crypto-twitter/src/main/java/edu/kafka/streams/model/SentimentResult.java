package edu.kafka.streams.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentimentResult {

    private String currency;
    private Double score;

    public static class SentimentResultSerde implements Serde<SentimentResult> {

        private final Gson gson = new GsonBuilder().create();

        @Override
        public Serializer<SentimentResult> serializer() {
            return (topic, tweet) -> gson.toJson(tweet).getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public Deserializer<SentimentResult> deserializer() {
            return (topic, data) -> gson.fromJson(new String(data), SentimentResult.class);
        }
    }
}
