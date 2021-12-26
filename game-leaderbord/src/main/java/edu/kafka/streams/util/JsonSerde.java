package edu.kafka.streams.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class JsonSerde<T> implements Serde<T> {

    private final Class<T> type;

    private final Gson gson = new GsonBuilder().create();

    public static <T> JsonSerde<T> forType(Class<T> type) {
        return new JsonSerde<>(type);
    }

    private JsonSerde(Class<T> type) {
        this.type = type;
    }

    @Override
    public Serializer<T> serializer() {
        return (topic, object) -> gson.toJson(object).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Deserializer<T> deserializer() {
        return (topic, data) -> gson.fromJson(new String(data), type);
    }
}
