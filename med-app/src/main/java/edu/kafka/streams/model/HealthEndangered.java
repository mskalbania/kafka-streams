package edu.kafka.streams.model;

import lombok.Data;

@Data
public class HealthEndangered {

    private Long heartRate;
    private Double temperature;

    public HealthEndangered(Long heartRate, Temperature temperature) {
        this.heartRate = heartRate;
        this.temperature = temperature.getValue();
    }
}
