package seedu.address.logic.parser.parameter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.cli.Options;

import seedu.address.logic.parser.exceptions.ParameterConflictException;

/**
 * Container for AbstractParameter objects.
 */
public class ParameterSet {
    private final List<AbstractParameter> parameterList;
    private Options options = new Options();

    public ParameterSet() {
        this.parameterList = new ArrayList<>();
    }

    private void refreshOptions() {
        this.options = new Options();
        for (AbstractParameter parameter : this.parameterList) {
            if (parameter.getFlag().equals("")) {
                // unnamed parameter, e.g. '1' in 'DELETE 1'
                continue;
            }
            this.options.addOption(parameter.asOption());
        }
    }

    /**
     * Adds a parameter to the set
     * @param parameter parameter to add
     * @throws ParameterConflictException if there are 2 parameters with the same flag
     */
    public void addParameter(AbstractParameter parameter) throws ParameterConflictException {
        this.parameterList.add(parameter);
        if (this.hasParameterConflict()) {
            throw new ParameterConflictException();
        }
        this.refreshOptions();
    }

    private boolean hasParameterConflict() {
        HashSet<String> set = new HashSet<>();
        parameterList.forEach(x -> set.add(x.getFlag()));
        return set.size() != parameterList.size();
    }

    /**
     * Get the set of commons-cli options generated from the parameters.
     * @return commons-cli options
     */
    public Options asOptions() {
        return options;
    }

    public List<AbstractParameter> getParameterList() {
        return parameterList;
    }
}
