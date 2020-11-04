package jimmy.mcgymmy.logic.parser;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import javafx.util.Pair;
import jimmy.mcgymmy.commons.core.Messages;
import jimmy.mcgymmy.logic.commands.CommandExecutable;
import jimmy.mcgymmy.logic.macro.MacroRunner;
import jimmy.mcgymmy.logic.macro.NewMacroCommand;
import jimmy.mcgymmy.logic.parser.exceptions.ParseException;
import jimmy.mcgymmy.model.macro.Macro;
import jimmy.mcgymmy.model.macro.MacroList;

/**
 * Parser for all McGymmy commands.
 */
public class McGymmyParser {
    private MacroList macroList;
    private final PrimitiveCommandParser primitiveCommandParser;

    /**
     * Constructor for McGymmyParser, but create a new macroList.
     */
    public McGymmyParser() {
        this(new MacroList());
    }

    /**
     * Constructor for McGymmyParser
     * @param macroList the macroList to supply
     */
    public McGymmyParser(MacroList macroList) {
        this.primitiveCommandParser = new PrimitiveCommandParser();
        this.macroList = macroList;
    }

    /**
     * Parses a raw input string from the user into an executable Command or macro.
     *
     * @param text raw input from the user
     * @return Command if parsing is successful
     * @throws ParseException if command is not found
     * @throws ParseException if a required argument to the command is not supplied
     * @throws ParseException if an argument to the command is not in the correct format
     */
    public CommandExecutable parse(String text) throws ParseException {
        Pair<String, String[]> headTail = ParserUtil.splitString(text);
        String commandName = headTail.getKey();
        String[] commandArguments = headTail.getValue();
        if (commandName.equals("macro")) {
            return parseCreateMacro(text);
        } else if (this.macroList.hasMacro(commandName)) {
            return this.parseRunMacro(commandName, commandArguments);
        } else if (this.primitiveCommandParser.hasCommand(commandName)) {
            return this.primitiveCommandParser.parsePrimitiveCommand(commandName, commandArguments);
        } else {
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

    public void setMacroList(MacroList macroList) {
        this.macroList = macroList;
    }

    public MacroList getMacroList() {
        return macroList;
    }

    /**
     * Creates a new macro using the String declaration.
     *
     * @param declaration Macro declaration string. Format in the user guide.
     * @return Macro that was created
     * @throws ParseException if declaration has the wrong format.
     */
    private CommandExecutable parseCreateMacro(String declaration) throws ParseException {
        // note: following line also trims whitespace between semicolons.
        Pair<String, String[]> headTail = ParserUtil.splitString(declaration, " *; *");
        String[] tailWithoutBlanks = Arrays.stream(headTail.getValue())
                .filter(s->!s.isBlank())
                .toArray(String[]::new);
        return new NewMacroCommand(headTail.getKey(), tailWithoutBlanks);
    }

    /**
     * Returns an executable form of macro with the given name.
     *
     * @param commandName name of the macro to run.
     * @param arguments arguments to the macro.
     * @return CommandExecutable of the macro.
     * @throws ParseException If the arguments to the macro are invalid.
     */
    private CommandExecutable parseRunMacro(String commandName, String[] arguments) throws ParseException {
        CommandLineParser commandLineParser = new DefaultParser();
        Macro macro = this.macroList.getMacro(commandName);
        Options options = macro.getOptions();
        try {
            CommandLine args = commandLineParser.parse(options, arguments);
            return MacroRunner.asCommandInstance(macro, args);
        } catch (org.apache.commons.cli.ParseException e) {
            String formattedHelp = ParserUtil.getUsageFromHelpFormatter(commandName, "", options);
            throw new ParseException(e.getMessage() + "\n" + formattedHelp);
        }
    }
}
