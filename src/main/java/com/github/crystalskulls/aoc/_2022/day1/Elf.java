package com.github.crystalskulls.aoc._2022.day1;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Elf implements Comparable<Elf> {

    private List<Integer> calories;

    public Elf() {
        this.calories = new ArrayList<>();
    }

    @Override
    public int compareTo(Elf anotherElf) {
        int totalCalories1 = this.getCalories().stream().reduce(0, Integer::sum);
        int totalCalories2 = anotherElf.getCalories().stream().reduce(0, Integer::sum);

        return Integer.compare(totalCalories1, totalCalories2);
    }
}
