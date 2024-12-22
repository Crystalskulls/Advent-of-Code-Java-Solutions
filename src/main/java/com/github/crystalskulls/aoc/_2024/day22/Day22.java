package com.github.crystalskulls.aoc._2024.day22;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day22 extends Puzzle {

    Map<Long, Long> secretNumberMap = new HashMap<>();
    Map<Long, LinkedHashMap<String, Integer>> buyerOfferings = new HashMap<>();

    public Day22() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2024/day22/input.txt";
    }

    @Override
    protected void solvePart1() {
        for (Long initialSecretNumber : secretNumberMap.keySet()) {
            Long secretNumber = initialSecretNumber;
            for (int i = 0; i < 2000; i++) {
                secretNumber = evolveSecretNumber(secretNumber);
            }
            secretNumberMap.put(initialSecretNumber, secretNumber);
        }
        System.out.println("Part 1: " + secretNumberMap.values().stream().mapToLong(l -> l).sum());
    }

    @Override
    protected void solvePart2() {
        for (Long initialSecretNumber : buyerOfferings.keySet()) {
            Long lastSecretNumber = initialSecretNumber;
            List<Integer> priceChanges = new ArrayList<>();
            for (int i = 0; i < 2000; i++) {
                Long nextSecretNumber = evolveSecretNumber(lastSecretNumber);
                priceChanges.add(calculatePriceChange(lastSecretNumber, nextSecretNumber));
                if(priceChanges.size() == 4) {
                    buyerOfferings.get(initialSecretNumber).putIfAbsent(priceChanges.toString(), parseOneDigit(String.valueOf(nextSecretNumber)));
                    priceChanges.removeFirst();
                }
                lastSecretNumber = nextSecretNumber;
            }
        }

        String bestSequence = "";
        int maxBananas = 0;
        System.out.println("Buyers: " + buyerOfferings.size());
        int z = buyerOfferings.size();
        for (Long initialSecretNumber : buyerOfferings.keySet()) {
            System.out.println("z: " + z);
            z--;
            System.out.println("Buyer: " + initialSecretNumber);
            System.out.println("Sequenzes List: " + buyerOfferings.get(initialSecretNumber).size());
            for (String currentSequence : buyerOfferings.get(initialSecretNumber).keySet()) {
                int sum = buyerOfferings.get(initialSecretNumber).get(currentSequence);
                for (Long otherSecretNumber : buyerOfferings.keySet()) {
                    if(Objects.equals(initialSecretNumber, otherSecretNumber)) {
                        continue;
                    }
                    Integer otherBananas = buyerOfferings.get(otherSecretNumber).get(currentSequence);
                    if(otherBananas != null) {
                        sum += otherBananas;
                    }
                }
                if(sum > maxBananas) {
                    maxBananas = sum;
                    bestSequence = currentSequence;
                }
            }
        }
        System.out.println("Part 2: " + bestSequence + " = " + maxBananas);
    }

    @Override
    protected void readInputData(String inputFile) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(FileReader.read(inputFile));
        while(matcher.find()) {
            Long secretNumber = Long.parseLong(matcher.group());
            secretNumberMap.put(secretNumber, secretNumber);
            buyerOfferings.put(secretNumber, new LinkedHashMap<>());
        }
    }

    Long evolveSecretNumber(Long secretNumber) {
        secretNumber = multiply(secretNumber, 64L);
        secretNumber = divide(secretNumber, 32L);
        return multiply(secretNumber, 2048L);
    }

    Long multiply(Long secretNumber, Long x) {
        Long r = secretNumber * x;
        secretNumber = mix(secretNumber, r);
        return prune(secretNumber);
    }

    Long divide(Long secretNumber, Long x) {
        Long r =  secretNumber / x;
        secretNumber = mix(secretNumber, r);
        return prune(secretNumber);
    }

    Long mix(Long secretNumber, Long x) {
        return secretNumber ^ x;
    }

    Long prune(Long secretNumber) {
        return secretNumber % 16777216;
    }

    private Integer calculatePriceChange(Long lastSecretNumber, Long nextSecretNumber) {
        String lastSecretNumberString = String.valueOf(lastSecretNumber);
        String nextSecretNumberString = String.valueOf(nextSecretNumber);
        int li = parseOneDigit(lastSecretNumberString);
        int ni = parseOneDigit(nextSecretNumberString);
        return ni - li;
    }

    private static int parseOneDigit(String secretNumberString) {
        return Integer.parseInt(secretNumberString.substring(secretNumberString.length() - 1));
    }
}