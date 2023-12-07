package com.github.crystalskulls.aoc._2023.day5;

import lombok.Data;

@Data
public class Seed {

    public Seed(Long number) {
        this.setNumber(number);
    }

    private Long number;
    private Long soil;
    private Long fertilizer;
    private Long water;
    private Long light;
    private Long temperature;
    private Long humidity;
    private Long location;
}
