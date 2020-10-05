package jimmy.mcgymmy.logic.parser.parameter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import jimmy.mcgymmy.logic.parser.CommandParserTestUtil;

public class OptionalParameterTest {
    @Test
    void optionalParameter_storesCorrectValue() throws Exception {
        OptionalParameter<Integer> testParameter = new OptionalParameter<>(
            "intparam",
            "i",
            "test",
            "test",
            String::length);
        testParameter.setValue("abcdef");
        assertEquals(testParameter.getValue().map(i -> i + 1), Optional.of(7));
    }

    @Test
    void optionalParameter_keepsParentRawValue() throws Exception {
        OptionalParameter<String> parameter = CommandParserTestUtil.makeDummyOptionalParameter("test", "t");
        parameter.setValue("poop");
        assertEquals(parameter.getRawValue(), Optional.of("poop"));
    }
}
