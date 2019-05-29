package execute;

import java.util.Map;

public class IntLiteralEx implements ExpressionOperand, ConditionOperand{
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean isTrue() {
        return value > 0;
    }

    @Override
    public Value execute(Scope scope, Map<String, FunctionEx> functions) {
        Value result = new Value();
        result.setValue(this.value);
        return result;
    }
}
