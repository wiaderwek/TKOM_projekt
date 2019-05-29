package execute;

import java.lang.Object;
import java.util.Map;

public class StringLiteralEx implements ExpressionOperand, ConditionOperand {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean isTrue() {
        return false;
    }

    @Override
    public Value execute(Scope scope, Map<String, FunctionEx> functions) {
        final Value result = new Value();
        result.setValue(this.value);
        return result;
    }
}
