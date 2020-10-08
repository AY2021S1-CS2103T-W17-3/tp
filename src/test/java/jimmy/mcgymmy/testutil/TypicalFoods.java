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

    public static final Food CHICKEN_RICE = new FoodBuilder().withName(new Name("Chicken Rice"))
            .withProtein("94351253").withFat("123")
            .withCarb("456")
            .withTags("lunch").build();
    public static final Food NASI_LEMAK = new FoodBuilder().withName(new Name("Nasi Alamak"))
            .withProtein("98765432")
            .withFat("321").withCarb("123")
            .withTags("dinner", "lunch").build();
    public static final Food CRISPY_FRIED_FISH = new FoodBuilder().withName(new Name("Crispy Fried Fish"))
            .withProtein("95352563").withFat("456").withCarb("654").build();
    public static final Food DANISH_COOKIES = new FoodBuilder().withName(new Name("Danish Cookies"))
            .withProtein("87652533").withFat("654").withCarb("456").withTags("lunch").build();
    public static final Food EGGS = new FoodBuilder().withName(new Name("Eggs")).withProtein("9482224")
            .withFat("246").withCarb("810").build();
    public static final Food FRUIT_CAKE = new FoodBuilder().withName(new Name("Fruit Cake")).withProtein("9482427")
            .withFat("987").withCarb("789").build();
    public static final Food GINGERBREAD = new FoodBuilder().withName(new Name("Gingerbread")).withProtein("9482442")
            .withFat("789").withCarb("987").build();

    // Manually added
    public static final Food HOT_PLATE = new FoodBuilder().withName(new Name("Hot Plate")).withProtein("8482424")
            .withFat("1234").withCarb("1234").build();
    public static final Food INDOMEE = new FoodBuilder().withName(new Name("Indomee")).withProtein("8482131")
            .withFat("1234").withCarb("1234").build();

    // Manually added - Food's details found in {@code CommandTestUtil}
    public static final Food APPLE = new FoodBuilder().withName(new Name("Apple")).withProtein("88888888")
            .withFat("1234").withCarb("1234").build();
    public static final Food BEANS = new FoodBuilder().withName(new Name("beans")).withProtein("88888888")
            .withFat("1234").withCarb("1234").build();

    public static final String KEYWORD_MATCHING_RICE = "Rice"; // A keyword that matches RICE

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
        return new ArrayList<>(Arrays.asList(CHICKEN_RICE, NASI_LEMAK, CRISPY_FRIED_FISH, DANISH_COOKIES, EGGS,
                FRUIT_CAKE, GINGERBREAD));
    }
}
