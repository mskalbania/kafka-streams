package edu.kafka.streams.service;

import edu.kafka.streams.model.SentimentResult;
import edu.kafka.streams.model.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SentimentAnalysisService {

    private static final List<String> SUPPORTED_CURRENCIES = List.of("bitcoin", "dogecoin");

    public List<SentimentResult> analyze(Tweet tweet) {
        List<SentimentResult> results = new ArrayList<>();
        SUPPORTED_CURRENCIES.forEach(currency -> {
            if (tweet.getContent().contains(currency)) {
                results.add(new SentimentResult(currency, new Random().nextDouble()));
            }
        });
        return results;
    }
}
