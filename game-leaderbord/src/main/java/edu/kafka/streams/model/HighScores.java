package edu.kafka.streams.model;

import lombok.ToString;

import java.util.TreeSet;

@ToString
public class HighScores {

    private final TreeSet<Enriched> topScores = new TreeSet<>();

    public HighScores add(Enriched enriched) {
        topScores.add(enriched);
        if (topScores.size() > 3) {
            topScores.remove(topScores.last());
        }
        return this;
    }
}
