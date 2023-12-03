package com.github.crystalskulls.aoc._2023.day3;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Symbol {

    private int x;
    private int y;
    private final List<Number> adjacentNumbers = new ArrayList<>();
}
