package com.github.crystalskulls.aoc._2023.day3;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Number {

    private int value;
    private List<Integer> xCoordinates;
    private int y;
}
