package com.github.crystalskulls.aoc._2023.day9;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class History {

    private final List<Integer> values;
    private final List<List<Integer>> sequences = new ArrayList<>();

    public History(List<Integer> values) {
        this.values = values;
        this.createSequences();
    }

    private void createSequences() {
        List<Integer> currentSequence = this.values;
        while(sequenceIsNotAllZeroes(currentSequence)) {
            List<Integer> newSequence = new ArrayList<>();
            for (int i = 0; i < currentSequence.size() - 1; i++) {
                newSequence.add(currentSequence.get(i + 1) - currentSequence.get(i));
            }
            this.sequences.add(newSequence);
            currentSequence = newSequence;
        }
    }

    public int predictNextValue() {
        for (int i = this.sequences.size() - 1; i >= 0; i--) {
            int n = i == this.sequences.size() - 1 ? 0 : this.sequences.get(i + 1).getLast();
            this.sequences.get(i).add(this.sequences.get(i).getLast() + n);
        }
        this.values.add(this.values.getLast() + this.sequences.getFirst().getLast());
        return this.values.getLast();
    }

    public int predictPreviousValue() {
        for (int i = this.sequences.size() - 1; i >= 0; i--) {
            int n = i == this.sequences.size() - 1 ? 0 : this.sequences.get(i + 1).getFirst();
            this.sequences.get(i).add(0, this.sequences.get(i).getFirst() - n);
        }
        this.values.add(0, this.values.getFirst() - this.sequences.getFirst().getFirst());
        return this.values.getFirst();
    }

    private boolean sequenceIsNotAllZeroes(List<Integer> sequence) {
        if(sequence.isEmpty()) {
            return true;
        }
        return !sequence.stream().allMatch(i -> i == 0);
    }
}
