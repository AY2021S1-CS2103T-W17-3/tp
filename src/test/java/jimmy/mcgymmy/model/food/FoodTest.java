package jimmy.mcgymmy.model.food;

import static jimmy.mcgymmy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class FoodTest {
    public static final String VALID_FOOD_NAME = "test food";
    public static final String VALID_FOOD_NAME_2 = "test food 2";
    public static final String INVALID_FOOD_NAME = "";

    public static final Protein PROTEIN = new Protein(2);
    public static final Protein PROTEIN_1 = new Protein(3);
    public static final Carbohydrate CARBOHYDRATE = new Carbohydrate(3);
    public static final Carbohydrate CARBOHYDRATE_1 = new Carbohydrate(4);
    public static final Fat FAT = new Fat(4);
    public static final Fat FAT_1 = new Fat(5);

    public static final Food COMPARED_FOOD = new Food(VALID_FOOD_NAME, PROTEIN, CARBOHYDRATE, FAT);
    public static final Food SAME_AS_COMPARED_FOOD = new Food(VALID_FOOD_NAME, PROTEIN, CARBOHYDRATE, FAT);
    public static final Food FOOD_W_DIFFERENT_NAME = new Food(VALID_FOOD_NAME_2, PROTEIN, CARBOHYDRATE, FAT);
    public static final Food FOOD_W_DIFFERENT_PROTEIN = new Food(VALID_FOOD_NAME, PROTEIN_1, CARBOHYDRATE, FAT);
    public static final Food FOOD_W_DIFFERENT_CARBS = new Food(VALID_FOOD_NAME, PROTEIN, CARBOHYDRATE_1, FAT);
    public static final Food FOOD_W_DIFFERENT_FAT = new Food(VALID_FOOD_NAME, PROTEIN, CARBOHYDRATE, FAT_1);

    @Test
    public void constructor_nullProtein_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new Food(VALID_FOOD_NAME, null, CARBOHYDRATE, FAT));
    }

    @Test
    public void constructor_nullCarbohydrate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new Food(VALID_FOOD_NAME, PROTEIN, null, FAT));
    }

    @Test
    public void constructor_nullFat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new Food(VALID_FOOD_NAME, PROTEIN, CARBOHYDRATE, null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            new Food(INVALID_FOOD_NAME, PROTEIN, CARBOHYDRATE, FAT));
    }

    @Test
    public void toStringIsCorrect() {
        String expected1 = "Food:test food\n"
            + "protein: 2\n"
            + "carbs: 3\n"
            + "fat: 4\n";
        assertEquals(COMPARED_FOOD.toString(), expected1);
        String expected2 = "Food:test food2\n"
            + "protein: 100\n"
            + "carbs: 20\n"
            + "fat: 10\n";
        assertEquals(new Food("test food2", 100, 20, 10).toString(), expected2);
    }

    @Test
    public void getCaloriesIsCorrect() {
        assertEquals(new Food("water", 0, 0, 0).getCalories(), 0);
        assertEquals(new Food("chimkenbreast", 30, 0, 0).getCalories(), 120);
        assertEquals(new Food("chimkenRice", 0, 30, 0).getCalories(), 120);
        assertEquals(new Food("sesameOil", 0, 0, 10).getCalories(), 90);
        assertEquals(new Food("chimkenRiceSet", 30, 30, 10).getCalories(), 330);
    }


    @Test
    public void equals() {
        // identical -> returns true
        assertEquals(COMPARED_FOOD, COMPARED_FOOD);

        // different object all field are the same -> returns true
        assertEquals(COMPARED_FOOD, SAME_AS_COMPARED_FOOD);

        // different name -> returns false
        assertFalse(COMPARED_FOOD.equals(FOOD_W_DIFFERENT_NAME));

        // different protein -> returns false
        assertFalse(COMPARED_FOOD.equals(FOOD_W_DIFFERENT_PROTEIN));

        // different carbohydrate -> returns false
        assertFalse(COMPARED_FOOD.equals(FOOD_W_DIFFERENT_CARBS));

        // different fat -> returns false
        assertFalse(COMPARED_FOOD.equals(FOOD_W_DIFFERENT_FAT));

        // different type -> returns false
        assertFalse(COMPARED_FOOD.equals(PROTEIN));
    }


}
