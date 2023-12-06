package com.github.crystalskulls.aoc.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public final Pattern numberPattern = Pattern.compile("\\d+");

    public List<Long> findNumbers(String numberString) {
        Matcher matcher = this.numberPattern.matcher(numberString);
        List<Long> numbers = new ArrayList<>();
        while (matcher.find()) {
            numbers.add(Long.parseLong(matcher.group()));
        }
        return numbers;
    }
}
