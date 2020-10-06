package jimmy.mcgymmy.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jimmy.mcgymmy.model.McGymmy;
import jimmy.mcgymmy.model.food.Food;
import jimmy.mcgymmy.model.food.Name;

/**
 * A utility class containing a list of {@code Food} objects to be used in tests.
 */
public class TypicalFoods {

    public static final Food ALICE = new FoodBuilder().withName(new Name("Alice Pauline"))
            .withProtein("1234").withFat("1234")
            .withCarb("1234")
            .withTags("friends").build();
    public static final Food BENSON = new FoodBuilder().withName(new Name("Benson Meier"))
            .withProtein("1234")
            .withFat("1234").withCarb("1234")
            .withTags("owesMoney", "friends").build();
    public static final Food CARL = new FoodBuilder().withName(new Name("Carl Kurz")).withCarb("95352563")
            .withFat("1234").withProtein("1234").build();
    public static final Food DANIEL = new FoodBuilder().withName(new Name("Daniel Meier")).withCarb("87652533")
            .withFat("1234").withProtein("1234").withTags("1234").build();
    public static final Food ELLE = new FoodBuilder().withName(new Name("Elle Meyer")).withCarb("9482224")
            .withFat("1234").withProtein("1234").build();
    public static final Food FIONA = new FoodBuilder().withName(new Name("Fiona Kunz")).withCarb("9482427")
            .withFat("1234").withProtein("1234").build();
    public static final Food GEORGE = new FoodBuilder().withName(new Name("George Best")).withCarb("9482442")
            .withFat("1234").withProtein("1234").build();

    // Manually added
    public static final Food HOON = new FoodBuilder().withName(new Name("Hoon Meier")).withCarb("8482424")
            .withFat("1234").withProtein("1234").build();
    public static final Food IDA = new FoodBuilder().withName(new Name("Ida Mueller")).withCarb("8482131")
            .withFat("1234").withProtein("1234").build();

    // Manually added - Food's details found in {@code CommandTestUtil}
    public static final Food AMY = new FoodBuilder().withName(new Name("amy")).withCarb("88888888")
            .withFat("1234").withProtein("1234").build();
    public static final Food BOB = new FoodBuilder().withName(new Name("bob")).withCarb("88888888")
            .withFat("1234").withProtein("1234").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalFoods() {
    } // prevents instantiation

    /**
     * Returns an {@code McGymmy} with all the typical persons.
     */
    public static McGymmy getTypicalMcGymmy() {
        McGymmy mg = new McGymmy();
        for (Food food : getTypicalFoodItems()) {
            mg.addFood(food);
        }
        return mg;
    }

    public static List<Food> getTypicalFoodItems() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
