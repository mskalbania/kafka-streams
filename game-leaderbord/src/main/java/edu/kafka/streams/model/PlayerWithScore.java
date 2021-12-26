package edu.kafka.streams.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public class PlayerWithScore {

    private final Player player;
    private final ScoreEvent scoreEvent;
}
