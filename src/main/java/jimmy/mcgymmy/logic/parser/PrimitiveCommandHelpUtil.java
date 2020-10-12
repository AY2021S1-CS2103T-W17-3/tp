package jimmy.mcgymmy.logic.parser;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.cli.Options;

import jimmy.mcgymmy.logic.commands.Command;
import jimmy.mcgymmy.logic.commands.CommandExecutable;
import jimmy.mcgymmy.logic.commands.CommandResult;
import jimmy.mcgymmy.logic.commands.exceptions.CommandException;
import jimmy.mcgymmy.logic.parser.parameter.AbstractParameter;
import jimmy.mcgymmy.logic.parser.parameter.ParameterSet;

public class PrimitiveCommandHelpUtil {
    private final Map<String, Supplier<Command>> commandTable;
    private final Map<String, String> commandDescriptionTable;
    PrimitiveCommandHelpUtil(Map<String, Supplier<Command>> commandTable, Map<String, String> commandDescriptionTable) {
        this.commandTable = commandTable;
        this.commandDescriptionTable = commandDescriptionTable;
    }
    // Creates the usage string using commons-cli's HelpFormatter and the createExampleCommand function
    public String getUsage(String commandName, ParameterSet parameterSet) {
        Options options = parameterSet.asOptions();
        String formattedHelp = ParserUtil.getUsageFromHelpFormatter(commandName,
                getUnnamedParameterUsage(parameterSet), options);
        return formattedHelp + "\nEXAMPLE: " + createExampleCommand(commandName, parameterSet.getParameterList());
    }

    private String getUnnamedParameterUsage(ParameterSet parameterSet) {
        return parameterSet.getUnnamedParameter()
                .map(param -> String.format("<arg> %s: %s", param.getName(), param.getDescription()))
                .orElseGet(() -> "");
    }

    private String createExampleCommand(String commandName, List<AbstractParameter> parameterList) {
        return commandName + " " + parameterList.stream()
                .map(p -> p.getFlag().equals("") ? p.getExample() : "-" + p.getFlag() + " " + p.getExample())
                .collect(Collectors.joining(" "));
    }

    private String formatAllCommandsHelp() {
        StringBuilder result = new StringBuilder("Here are all the available commands."
                + "\n\nType: 'help [COMMAND]' for more info on a specific command.\n");
        for (String commandName : commandDescriptionTable.keySet()) {
            result.append(String.format("\n%s: %s", commandName, commandDescriptionTable.get(commandName)));
        }
        return result.toString();
    }

    public CommandExecutable newHelpCommand(String commandName) {
        return model -> {
            if (!commandTable.containsKey(commandName)) {
                throw new CommandException("Error: That command does not exist.");
            }
            Command usageOf = commandTable.get(commandName).get();
            return new CommandResult(getUsage(commandName, usageOf.getParameterSet()));
        };
    }

    public CommandExecutable newHelpCommand() {
        return model -> new CommandResult(formatAllCommandsHelp());
    }
}
