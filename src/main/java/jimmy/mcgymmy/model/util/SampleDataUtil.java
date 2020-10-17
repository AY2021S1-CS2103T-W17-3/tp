package jimmy.mcgymmy.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import jimmy.mcgymmy.model.McGymmy;
import jimmy.mcgymmy.model.ReadOnlyMcGymmy;
import jimmy.mcgymmy.model.date.Date;
import jimmy.mcgymmy.model.food.Carbohydrate;
import jimmy.mcgymmy.model.food.Fat;
import jimmy.mcgymmy.model.food.Food;
import jimmy.mcgymmy.model.food.Name;
import jimmy.mcgymmy.model.food.Protein;
import jimmy.mcgymmy.model.tag.Tag;

/**
 * Contains utility methods for populating {@code McGymmy} with sample data.
 */
public class SampleDataUtil {
    public static Food[] getSamplePersons() {
        return new Food[] {
            new Food(new Name("Chicken Rice"), new Protein(200), new Fat(300),
                    new Carbohydrate(100), getTagSet("Lunch"),
                    new Date("20/4/2020")),
            new Food(new Name("Rice Bowl"), new Protein(300), new Fat(200),
                    new Carbohydrate(100), getTagSet("Lunch", "Dinner"),
                    new Date("2/9/2020")),
            new Food(new Name("Fried Chicken"), new Protein(200), new Fat(100),
                    new Carbohydrate(10), getTagSet("Tasty"),
                    new Date("23/7/2019")),
            new Food(new Name("Nasi Lemak"), new Protein(20), new Fat(17),
                    new Carbohydrate(31), getTagSet("Lunch"),
                    Date.currentDate()),
        };
    }

    public static ReadOnlyMcGymmy getSampleMcGymmy() {
        McGymmy sampleMG = new McGymmy();
        for (Food sampleFood : getSamplePersons()) {
            sampleMG.addFood(sampleFood);
        }
        return sampleMG;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
