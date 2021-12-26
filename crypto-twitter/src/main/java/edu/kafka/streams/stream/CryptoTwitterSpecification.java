package edu.kafka.streams.stream;

import edu.kafka.streams.model.SentimentResult;
import edu.kafka.streams.model.Tweet;
import edu.kafka.streams.service.SentimentAnalysisService;
import edu.kafka.streams.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.util.Map;

@RequiredArgsConstructor
public class CryptoTwitterSpecification {

    private final TranslationService translationService;
    private final SentimentAnalysisService sentimentAnalysisService;

    public Topology create() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<Void, Tweet> tweets = streamsBuilder.stream("tweets", Consumed.with(Serdes.Void(), new Tweet.TweetSerde()))
                .filterNot((key, value) -> value.getLanguage() == null || value.getLanguage().isBlank())
                .filterNot((key, value) -> value.getIsRetweet());

        Map<String, KStream<Void, Tweet>> branched = tweets.split(Named.as("language-"))
                .branch((key, value) -> "ENG".equals(value.getLanguage()), Branched.as("english"))
                .defaultBranch(Branched.as("non-english"));

        KStream<Void, Tweet> translated = branched.get("language-non-english")
                .mapValues(translationService::translateToEng);

        branched.get("language-english").merge(translated)
                .flatMapValues(sentimentAnalysisService::analyze)
                .to("analyzed", Produced.with(Serdes.Void(), new SentimentResult.SentimentResultSerde()));

        return streamsBuilder.build();
    }
}
