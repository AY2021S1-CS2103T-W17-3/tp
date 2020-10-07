package jimmy.mcgymmy.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import jimmy.mcgymmy.commons.core.GuiSettings;
import jimmy.mcgymmy.model.food.Food;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Food> PREDICATE_SHOW_ALL_FOODS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getMcGymmyFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setMcGymmyFilePath(Path mcGymmyFilePath);

    /**
     * Returns the AddressBook
     */
    void setMcGymmy(ReadOnlyMcGymmy addressBook);

    /**
     * Returns the McGymmy
     */
    ReadOnlyMcGymmy getMcGymmy();

    /**
     * Returns true if a food with the same identity as {@code food} exists in the address book.
     */
    boolean hasFood(Food food);

    /**
     * Deletes the given food.
     * The food must exist in the address book.
     */
    void deleteFood(Food food);

    /**
     * Adds the given food.
     * {@code food} must not already exist in the address book.
     */
    void addFood(Food food);

    /**
     * Replaces the given food {@code target} with {@code editedFood}.
     * {@code target} must exist in the address book.
     * The food identity of {@code editedFood} must not be the same as another existing food in the address book.
     */
    void setFood(Food target, Food editedFood);

    /**
     * Returns an unmodifiable view of the filtered food list
     */
    ObservableList<Food> getFilteredFoodList();

    /**
     * Updates the filter of the filtered food list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFoodList(Predicate<Food> predicate);
}
