package edu.kafka.streams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeartBeat implements Vital {

    private String timestamp;

    @Override
    public String getTimeStamp() {
        return this.timestamp;
    }
}
