package com.github.crystalskulls.aoc;

import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;

public class CommandLineInterface {

    private CommandLineInterface() {}

    public static CommandLine parseArguments(String[] args) {
        CommandLineParser commandLineParser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        Options options = buildOptions();
        try {
            return commandLineParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helpFormatter.printHelp("java -jar advent-of-code-1.0-SNAPSHOT.jar", options);
            System.exit(1);
        }
        return null;
    }

    private static Options buildOptions() {
        Options options = new Options();
        Option year = buildRequiredOptionWithArg("y", "year", "year", "Solve puzzles of this year.");
        Option day = buildRequiredOptionWithArg("d", "day", "day", "Solve puzzle of this day.");
        options.addOption(year).addOption(day);
        return options;
    }

    @NotNull
    private static Option buildRequiredOptionWithArg(String option, String longOpt, String argName, String description) {
        return Option
                .builder(option)
                .longOpt(longOpt)
                .argName(argName)
                .hasArg()
                .required()
                .desc(description)
                .type(Integer.class)
                .build();
    }
}
