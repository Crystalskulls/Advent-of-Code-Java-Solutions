package com.github.crystalskulls.aoc._2023.day5;

import com.github.crystalskulls.aoc.common.FileReader;
import com.github.crystalskulls.aoc.common.Puzzle;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 extends Puzzle {

    private final List<Seed> seeds = new ArrayList<>();
    private final List<Range> seedRanges = new ArrayList<>();
    private final List<Map<Range, Range>> seedToSoilMap = new ArrayList<>();
    private final List<Map<Range, Range>> soilToFertilizerMap = new ArrayList<>();
    private final List<Map<Range, Range>> fertilizerToWaterMap = new ArrayList<>();
    private final List<Map<Range, Range>> waterToLightMap = new ArrayList<>();
    private final List<Map<Range, Range>> lightToTemperatureMap = new ArrayList<>();
    private final List<Map<Range, Range>> temperatureToHumidityMap = new ArrayList<>();
    private final List<Map<Range, Range>> humidityToLocationMap = new ArrayList<>();


    private final Pattern numberPattern = Pattern.compile("\\d+");

    public Day5() {
        this.inputFile = "src/main/java/com/github/crystalskulls/aoc/_2023/day5/input.txt";
    }

    @Override
    protected void solvePart1() {
        this.seeds.forEach(seed -> {
            this.seedToSoilMap.forEach(rangeMap -> {
                rangeMap.forEach((sourceRange, destinationRange) -> {
                    if(seed.getNumber() >= sourceRange.getStart() && seed.getNumber() <= sourceRange.getEnd()) {
                        Long delta = seed.getNumber() - sourceRange.getStart();
                        seed.setSoil(destinationRange.getStart() + delta);
                    }
                });
            });
            if(seed.getSoil() == null) {
                seed.setSoil(seed.getNumber());
            }
        });

        this.seeds.forEach(seed -> {
            this.soilToFertilizerMap.forEach(rangeMap -> {
                rangeMap.forEach((sourceRange, destinationRange) -> {
                    if(seed.getSoil() >= sourceRange.getStart() && seed.getSoil() <= sourceRange.getEnd()) {
                        Long delta = seed.getSoil() - sourceRange.getStart();
                        seed.setFertilizer(destinationRange.getStart() + delta);
                    }
                });
            });
            if(seed.getFertilizer() == null) {
                seed.setFertilizer(seed.getSoil());
            }
        });

        this.seeds.forEach(seed -> {
            this.fertilizerToWaterMap.forEach(rangeMap -> {
                rangeMap.forEach((sourceRange, destinationRange) -> {
                    if(seed.getFertilizer() >= sourceRange.getStart() && seed.getFertilizer() <= sourceRange.getEnd()) {
                        Long delta = seed.getFertilizer() - sourceRange.getStart();
                        seed.setWater(destinationRange.getStart() + delta);
                    }
                });
            });
            if(seed.getWater() == null) {
                seed.setWater(seed.getFertilizer());
            }
        });

        this.seeds.forEach(seed -> {
            this.waterToLightMap.forEach(rangeMap -> {
                rangeMap.forEach((sourceRange, destinationRange) -> {
                    if(seed.getWater() >= sourceRange.getStart() && seed.getWater() <= sourceRange.getEnd()) {
                        Long delta = seed.getWater() - sourceRange.getStart();
                        seed.setLight(destinationRange.getStart() + delta);
                    }
                });
            });
            if(seed.getLight() == null) {
                seed.setLight(seed.getWater());
            }
        });

        this.seeds.forEach(seed -> {
            this.lightToTemperatureMap.forEach(rangeMap -> {
                rangeMap.forEach((sourceRange, destinationRange) -> {
                    if(seed.getLight() >= sourceRange.getStart() && seed.getLight() <= sourceRange.getEnd()) {
                        Long delta = seed.getLight() - sourceRange.getStart();
                        seed.setTemperature(destinationRange.getStart() + delta);
                    }
                });
            });
            if(seed.getTemperature() == null) {
                seed.setTemperature(seed.getLight());
            }
        });

        this.seeds.forEach(seed -> {
            this.temperatureToHumidityMap.forEach(rangeMap -> {
                rangeMap.forEach((sourceRange, destinationRange) -> {
                    if(seed.getTemperature() >= sourceRange.getStart() && seed.getTemperature() <= sourceRange.getEnd()) {
                        Long delta = seed.getTemperature() - sourceRange.getStart();
                        seed.setHumidity(destinationRange.getStart() + delta);
                    }
                });
            });
            if(seed.getHumidity() == null) {
                seed.setHumidity(seed.getTemperature());
            }
        });

        this.seeds.forEach(seed -> {
            this.humidityToLocationMap.forEach(rangeMap -> {
                rangeMap.forEach((sourceRange, destinationRange) -> {
                    if(seed.getHumidity() >= sourceRange.getStart() && seed.getHumidity() <= sourceRange.getEnd()) {
                        Long delta = seed.getHumidity() - sourceRange.getStart();
                        seed.setLocation(destinationRange.getStart() + delta);
                    }
                });
            });
            if(seed.getLocation() == null) {
                seed.setLocation(seed.getHumidity());
            }
        });
        Long lowestLocationNumber = this.seeds.getFirst().getLocation();
        for (Seed seed : this.seeds) {
            lowestLocationNumber = Math.min(lowestLocationNumber, seed.getLocation());
        }
        System.out.println("Part 1: " + lowestLocationNumber);
    }

    @Override
    protected void solvePart2() {
        for(Long i = 0L; i <= Long.MAX_VALUE; i++) {
            System.out.println(i);
            Seed testSeed = new Seed(null);
            testSeed.setLocation(i);
            List<Seed> seeds = List.of(testSeed);
            seeds.forEach(seed -> {
                this.humidityToLocationMap.forEach(rangeMap -> {
                    rangeMap.forEach((sourceRange, destinationRange) -> {
                        if(seed.getLocation() >= destinationRange.getStart() && seed.getLocation() <= destinationRange.getEnd()) {
                            Long delta = seed.getLocation() - destinationRange.getStart();
                            seed.setHumidity(sourceRange.getStart() + delta);
                        }
                    });
                });
                if(seed.getHumidity() == null) {
                    seed.setHumidity(seed.getLocation());
                }
            });

            seeds.forEach(seed -> {
                this.temperatureToHumidityMap.forEach(rangeMap -> {
                    rangeMap.forEach((sourceRange, destinationRange) -> {
                        if(seed.getHumidity() >= destinationRange.getStart() && seed.getHumidity() <= destinationRange.getEnd()) {
                            Long delta = seed.getHumidity() - destinationRange.getStart();
                            seed.setTemperature(sourceRange.getStart() + delta);
                        }
                    });
                });
                if(seed.getTemperature() == null) {
                    seed.setTemperature(seed.getHumidity());
                }
            });

            seeds.forEach(seed -> {
                this.lightToTemperatureMap.forEach(rangeMap -> {
                    rangeMap.forEach((sourceRange, destinationRange) -> {
                        if(seed.getTemperature() >= destinationRange.getStart() && seed.getTemperature() <= destinationRange.getEnd()) {
                            Long delta = seed.getTemperature() - destinationRange.getStart();
                            seed.setLight(sourceRange.getStart() + delta);
                        }
                    });
                });
                if(seed.getLight() == null) {
                    seed.setLight(seed.getTemperature());
                }
            });

            seeds.forEach(seed -> {
                this.waterToLightMap.forEach(rangeMap -> {
                    rangeMap.forEach((sourceRange, destinationRange) -> {
                        if(seed.getLight() >= destinationRange.getStart() && seed.getLight() <= destinationRange.getEnd()) {
                            Long delta = seed.getLight() - destinationRange.getStart();
                            seed.setWater(sourceRange.getStart() + delta);
                        }
                    });
                });
                if(seed.getWater() == null) {
                    seed.setWater(seed.getLight());
                }
            });

            seeds.forEach(seed -> {
                this.fertilizerToWaterMap.forEach(rangeMap -> {
                    rangeMap.forEach((sourceRange, destinationRange) -> {
                        if(seed.getWater() >= destinationRange.getStart() && seed.getWater() <= destinationRange.getEnd()) {
                            Long delta = seed.getWater() - destinationRange.getStart();
                            seed.setFertilizer(sourceRange.getStart() + delta);
                        }
                    });
                });
                if(seed.getFertilizer() == null) {
                    seed.setFertilizer(seed.getWater());
                }
            });

            seeds.forEach(seed -> {
                this.soilToFertilizerMap.forEach(rangeMap -> {
                    rangeMap.forEach((sourceRange, destinationRange) -> {
                        if(seed.getFertilizer() >= destinationRange.getStart() && seed.getFertilizer() <= destinationRange.getEnd()) {
                            Long delta = seed.getFertilizer() - destinationRange.getStart();
                            seed.setSoil(sourceRange.getStart() + delta);
                        }
                    });
                });
                if(seed.getSoil() == null) {
                    seed.setSoil(seed.getFertilizer());
                }
            });

            seeds.forEach(seed -> {
                this.seedToSoilMap.forEach(rangeMap -> {
                    rangeMap.forEach((sourceRange, destinationRange) -> {
                        if(seed.getSoil() >= destinationRange.getStart() && seed.getSoil() <= destinationRange.getEnd()) {
                            Long delta = seed.getSoil() - destinationRange.getStart();
                            seed.setNumber(sourceRange.getStart() + delta);
                        }
                    });
                });
                if(seed.getNumber() == null) {
                    seed.setNumber(seed.getSoil());
                }
            });

            this.seedRanges.forEach(range -> {
                if(range.getStart() <= seeds.getFirst().getNumber() && seeds.getFirst().getNumber() <= range.getEnd()) {
                    System.out.println("found");
                    System.out.println("Part 2: " + seeds.getFirst().getLocation());
                    System.exit(1);
                }
            });
        }

    }

    @Override
    protected void readInputData(String inputFile) {
        List<String> inputBlocks = FileReader.readAllLines(inputFile, "\n\n");
        parseSeeds(inputBlocks.getFirst());
        parseToSeedRanges(inputBlocks.getFirst());
        parseSeedToSoil(inputBlocks.get(1));
        parseSoilToFertilizer(inputBlocks.get(2));
        parseFertilizerToWater(inputBlocks.get(3));
        parseWaterToLight(inputBlocks.get(4));
        parseLightToTemperature(inputBlocks.get(5));
        parseTemperatureToHumidity(inputBlocks.get(6));
        parseHumidityToLocation(inputBlocks.get(7));
    }

    private void parseToSeedRanges(String input) {
        List<Long> seedNumbers = this.findNumbers(input);
        for (int i = 0; i < seedNumbers.size(); i++) {
            if(i % 2 == 0) {
                this.seedRanges.add(new Range(seedNumbers.get(i), seedNumbers.get(i) + seedNumbers.get(i+1) - 1));
            }
        }
    }

    private void parseSeedToSoil(String input) {
        parseInputToMap(input, this.seedToSoilMap);
    }

    private void parseSoilToFertilizer(String input) {
        parseInputToMap(input,this.soilToFertilizerMap);
    }

    private void parseFertilizerToWater(String input) {
        parseInputToMap(input,this.fertilizerToWaterMap);
    }

    private void parseWaterToLight(String input) {
        parseInputToMap(input,this.waterToLightMap);
    }

    private void parseLightToTemperature(String input) {
        parseInputToMap(input,this.lightToTemperatureMap);
    }

    private void parseTemperatureToHumidity(String input) {
        parseInputToMap(input,this.temperatureToHumidityMap);
    }

    private void parseHumidityToLocation(String input) {
        parseInputToMap(input,this.humidityToLocationMap);
    }

    private void parseInputToMap(String input, List<Map<Range, Range>> map) {
        List<String> maps = new ArrayList<>(Arrays.asList(input.split("\n")));
        maps.remove(0);

        maps.forEach(mapString -> {
            List<Long> numbers = this.findNumbers(mapString);
            Long destinationRangeStart = numbers.getFirst();
            Long sourceRangeStart = numbers.get(1);
            Long rangeLength = numbers.getLast();
            map.add(Map.of(
                            new Range(sourceRangeStart, sourceRangeStart + rangeLength -1),
                            new Range(destinationRangeStart, destinationRangeStart + rangeLength -1)
                    )
            );
        });
    }

    private void parseSeeds(String input) {
        List<Long> seedNumbers = this.findNumbers(input);
        for (Long seedNumber : seedNumbers) {
            this.seeds.add(new Seed(seedNumber));
        }
    }

    private List<Long> findNumbers(String numberString) {
        Matcher matcher = this.numberPattern.matcher(numberString);
        List<Long> numbers = new ArrayList<>();
        while (matcher.find()) {
            numbers.add(Long.parseLong(matcher.group()));
        }
        return numbers;
    }
}
