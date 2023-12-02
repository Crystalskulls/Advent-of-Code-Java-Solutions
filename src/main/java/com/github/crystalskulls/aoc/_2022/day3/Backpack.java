package com.github.crystalskulls.aoc._2022.day3;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class Backpack {

    private char[] firstCompartment;
    private char[] secondCompartment;
    private char[] items;

    public Backpack(String items) {
        int middle = items.length() / 2;
        this.setFirstCompartment(items.substring(0, middle).toCharArray());
        this.setSecondCompartment(items.substring(middle).toCharArray());
        this.items = items.toCharArray();
        Arrays.sort(this.getFirstCompartment());
        Arrays.sort(this.getSecondCompartment());
        Arrays.sort(this.items);
    }
}
