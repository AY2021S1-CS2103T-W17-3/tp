package jimmy.mcgymmy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import jimmy.mcgymmy.commons.core.Messages;
import jimmy.mcgymmy.commons.core.index.Index;
import jimmy.mcgymmy.logic.parser.CommandParserTestUtil;
import jimmy.mcgymmy.model.Model;
import jimmy.mcgymmy.model.ModelManager;
import jimmy.mcgymmy.model.UserPrefs;
import jimmy.mcgymmy.model.food.Food;
import jimmy.mcgymmy.testutil.TypicalIndexes;
import jimmy.mcgymmy.testutil.TypicalFoods;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {
    /*
    TODO:
    1. fails when index is out of bounds
    2. succeeds when index is valid
     */

    private Model model = new ModelManager(TypicalFoods.getTypicalMcGymmy(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Food foodToDelete = model.getFilteredFoodList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand();
        deleteCommand.setParameters(new CommandParserTestUtil.ParameterStub<>("", TypicalIndexes.INDEX_FIRST_PERSON));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, foodToDelete);

        ModelManager expectedModel = new ModelManager(model.getMcGymmy(), new UserPrefs());
        expectedModel.deleteFood(foodToDelete);

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand();
        deleteCommand.setParameters(new CommandParserTestUtil.ParameterStub<>("", outOfBoundIndex));

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Food foodToDelete = model.getFilteredFoodList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand();
        deleteCommand.setParameters(new CommandParserTestUtil.ParameterStub<>("", TypicalIndexes.INDEX_FIRST_PERSON));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, foodToDelete);

        Model expectedModel = new ModelManager(model.getMcGymmy(), new UserPrefs());
        expectedModel.deleteFood(foodToDelete);
        showNoPerson(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMcGymmy().getFoodList().size());

        DeleteCommand deleteCommand = new DeleteCommand();
        deleteCommand.setParameters(new CommandParserTestUtil.ParameterStub<>("", outOfBoundIndex));

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredFoodList(p -> false);

        assertTrue(model.getFilteredFoodList().isEmpty());
    }
}
