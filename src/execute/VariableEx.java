package execute;

import java.lang.Object;
import java.util.Map;

public class VariableEx implements ExpressionOperand, Assignable, ConditionOperand {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isTrue() {
        return false;
    }

    @Override
    public Value execute(Scope scope, Map<String, FunctionEx> functions) {
        final Value value = scope.getValue(name);
        if(value.isCalculated()) {
            return  value;
        } else {
            value.setCalculated(true);
            value.setValue(value.getInstructionValue().execute(scope, functions).getValue());
            return value;
        }

    }
}
