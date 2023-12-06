package com.github.crystalskulls.aoc.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public final Pattern numberPattern = Pattern.compile("\\d+");

    public <T extends Number> List<T> findNumbers(String numberString, Function<String, T> numberConverter) {
        Matcher matcher = this.numberPattern.matcher(numberString);
        List<T> numbers = new ArrayList<>();
        while (matcher.find()) {
            numbers.add((numberConverter.apply(matcher.group())));
        }
        return numbers;
    }
}
