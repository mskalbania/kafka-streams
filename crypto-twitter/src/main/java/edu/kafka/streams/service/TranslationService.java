package edu.kafka.streams.service;

import edu.kafka.streams.model.Tweet;

//This supposed to be translation service but its irrelevant to streams example
public class TranslationService {

    public Tweet translateToEng(Tweet tweet) {
        return new Tweet(tweet.getId(), tweet.getIsRetweet(), tweet.getContent(), "ENG");
    }
}
