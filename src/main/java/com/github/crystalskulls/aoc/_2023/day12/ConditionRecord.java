package com.github.crystalskulls.aoc._2023.day12;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ConditionRecord {

    String row;
    int currentSpringGroup;

    public ConditionRecord(String row) {
        this.row = row;
    }
}
