package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

<<<<<<< Updated upstream:src/main/java/seedu/address/logic/commands/DeleteCommand.java
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.parameter.Parameter;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
=======
import jimmy.mcgymmy.commons.core.Messages;
import jimmy.mcgymmy.commons.core.index.Index;
import jimmy.mcgymmy.logic.commands.exceptions.CommandException;
import jimmy.mcgymmy.logic.parser.ParserUtil;
import jimmy.mcgymmy.logic.parser.parameter.Parameter;
import jimmy.mcgymmy.model.Model;
import jimmy.mcgymmy.model.food.Food;
>>>>>>> Stashed changes:src/main/java/jimmy/mcgymmy/logic/commands/DeleteCommand.java

/**
 * Deletes a food identified using it's displayed index from the mcgymmy.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Food: %1$s";

    private Parameter<Index> indexParameter = this.addParameter(
        "index",
        "",
        "index number used in the displayed food list.",
        "2",
        ParserUtil::parseIndex
    );

    void setParameters(Parameter<Index> indexParameter) {
        this.indexParameter = indexParameter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Food> lastShownList = model.getFilteredFoodList();
        Index targetIndex = indexParameter.consume();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Food foodToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteFood(foodToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, foodToDelete));
    }
}
