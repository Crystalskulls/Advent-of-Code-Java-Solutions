package com.github.crystalskulls.aoc._2023.day4;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Scratchcard {

    private List<Integer> winningNumbers;
    private List<Integer> yourNumbers;

    public int findMatchingNumbers() {
        return this.getYourNumbers().stream().filter(this.getWinningNumbers()::contains).toList().size();
    }
}
