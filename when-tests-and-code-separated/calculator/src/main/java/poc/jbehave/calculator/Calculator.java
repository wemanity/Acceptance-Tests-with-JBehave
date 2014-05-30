/**
 * 
 */
package poc.jbehave.calculator;

import java.util.HashMap;
import java.util.Map;

/**
 * Calculateur.
 * 
 * @author Xavier Pigeon
 */
public class Calculator {

    private final Map<String, Integer> context;

    public Calculator() {
        context = new HashMap<String, Integer>();
    }

    public void defineVariable(String variable, int value) {
        context.put(variable, value);
    }

    public void addToVariable(String variable, int value) {
        int existing = getVariableValueOrFail(variable);
        context.put(variable, value + existing);
    }

    public int getVariableValue(String variable) {
        return getVariableValueOrFail(variable);
    }

    protected int getVariableValueOrFail(String variable) {
        Integer existing = context.get(variable);

        if (existing == null) {
            throw new IllegalStateException("Variable <" + variable + "> is not defined!");
        } else {
            return existing;
        }
    }
}
