package edu.kafka.streams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temperature implements Vital{

    private String timestamp;
    private Double value;

    @Override
    public String getTimeStamp() {
        return null;
    }
}
