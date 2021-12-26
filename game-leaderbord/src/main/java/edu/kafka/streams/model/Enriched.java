package edu.kafka.streams.model;

import lombok.Data;

@Data
public class Enriched implements Comparable<Enriched> {

    private Long playerId;
    private Long productId;
    private String playerName;
    private String gameName;
    private Double score;

    public Enriched(PlayerWithScore playerWithScore, Product product) {
        this.playerId = playerWithScore.getPlayer().getId();
        this.productId = product.getId();
        this.playerName = playerWithScore.getPlayer().getName();
        this.gameName = product.getName();
        this.score = playerWithScore.getScoreEvent().getScore();
    }

    @Override
    public int compareTo(Enriched o) {
        return Double.compare(o.score, this.getScore());
    }
}
