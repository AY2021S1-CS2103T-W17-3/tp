package jimmy.mcgymmy.model;

import java.util.Stack;
import java.util.function.Predicate;

import javafx.util.Pair;
import jimmy.mcgymmy.model.food.Food;
import jimmy.mcgymmy.model.macro.MacroList;

class History {
    protected final Stack<Pair<McGymmy, Pair<Predicate<Food>, MacroList>>> stack;

    History() {
        stack = new Stack<>();
    }

    /**
     * Saves the mcGymmy, predicate and macrolist of <code>ModelManager</code> to history
     */
    void save(ModelManager modelManager) {
        McGymmy mcGymmy = new McGymmy(modelManager.getMcGymmy());
        Predicate<Food> predicate = modelManager.getFilterPredicate();
        MacroList macroList = modelManager.getMacroList();
        stack.push(new Pair<>(mcGymmy, new Pair<>(predicate, macroList)));
    }

    /**
     * @return True if the history is empty
     */
    boolean empty() {
        return stack.empty();
    }

    /**
     * Get the previous state from history
     * @throws AssertionError
     */
    void pop() throws AssertionError {
        assert !stack.empty() : "History is empty";
        stack.pop();
    }

    McGymmy peekMcGymmy() throws AssertionError {
        assert !stack.empty() : "History is empty";
        return stack.peek().getKey();
    }

    Predicate<Food> peekPredicate() throws AssertionError {
        assert !stack.empty() : "History is empty";
        return stack.peek().getValue().getKey();
    }

    MacroList peekMacroList() throws AssertionError {
        assert !stack.empty() : "History is empty";
        return stack.peek().getValue().getValue();
    }
}

