package jimmy.mcgymmy.logic.macro;

import java.util.Arrays;
import jimmy.mcgymmy.logic.commands.CommandExecutable;
import jimmy.mcgymmy.logic.commands.CommandResult;
import jimmy.mcgymmy.logic.commands.exceptions.CommandException;
import jimmy.mcgymmy.logic.macro.exceptions.DuplicateMacroException;
import jimmy.mcgymmy.logic.parser.exceptions.ParseException;
import jimmy.mcgymmy.model.Model;

public class NewMacroCommand implements CommandExecutable {
    private final MacroList macroList;
    private final String argumentDeclaration;
    private final String[] statements;

    public NewMacroCommand(MacroList macroList, String argumentDeclaration, String[] statements) {
        this.macroList = macroList; // should this be part of the model?
        this.argumentDeclaration = argumentDeclaration;
        this.statements = statements;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String[] splitDeclaration = argumentDeclaration.split("\\s+");
        try {
            Macro newMacro = new Macro(
                    splitDeclaration[1],
                    Arrays.copyOfRange(splitDeclaration, 2, splitDeclaration.length),
                    this.statements);
            this.macroList.addMacro(newMacro);
            return new CommandResult(newMacro.getName() + " successfully added.");
        } catch (DuplicateMacroException | ParseException e) {
            throw new CommandException(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            // TODO better errors?
            throw new CommandException("Error: missing macro name.");
        }
    }
}
