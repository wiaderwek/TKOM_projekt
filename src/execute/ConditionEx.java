package execute;

import model.Condition;
import model.data.TokenType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConditionEx implements ConditionOperand {
    private boolean isNegated;
    public TokenType operation = null;
    public List<ConditionOperand> operands = new LinkedList<>();

    @Override
    public boolean isTrue() {
        return false;
    }

    public void setIsNegated(boolean isNegated) {
        this.isNegated = isNegated;
    }

    public void setOperation(final TokenType operation) {
        this.operation = operation;
    }

    public void addOpernad(final ConditionOperand operand) {
        operands.add(operand);
    }

    @Override
    public Value execute(Scope scope, Map<String, FunctionEx> functions) {
        final Value result = new Value();
        result.setBoolean(true);
        switch (operation) {
            case OR:
                for(final ConditionOperand operand : operands) {
                    if(operand.execute(scope, functions).isTrue()) {
                        result.setTrue(true);
                        return result;
                    }
                }
                result.setTrue(false);
                return  result;
            case AND:
                for (final ConditionOperand operand : operands) {
                    if(!operand.execute(scope, functions).isTrue()) {
                        result.setTrue(false);
                        return result;
                    }
                }
                result.setTrue(true);
                return result;
            case EQUAL:
                Value left = operands.get(0).execute(scope, functions);
                Value right = operands.get(1).execute(scope, functions);
                result.setTrue(left.getValue().equals(right.getValue()));
                break;
            case BANG_EQUAL:
                left = operands.get(0).execute(scope, functions);
                right = operands.get(1).execute(scope, functions);
                result.setTrue(!left.getValue().equals(right.getValue()));
                break;
            case LESS:
                int l = (int) operands.get(0).execute(scope, functions).getValue();
                int r = (int) operands.get(1).execute(scope, functions).getValue();
                result.setTrue(l < r);
                break;
            case LESS_EQUAL:
                l = (int) operands.get(0).execute(scope, functions).getValue();
                r = (int) operands.get(1).execute(scope, functions).getValue();
                result.setTrue(l <= r);
                break;
            case GREATER:
                l = (int) operands.get(0).execute(scope, functions).getValue();
                r = (int) operands.get(1).execute(scope, functions).getValue();
                result.setTrue(l > r);
                break;
            case GREATER_EQUAL:
                l = (int) operands.get(0).execute(scope, functions).getValue();
                r = (int) operands.get(1).execute(scope, functions).getValue();
                result.setTrue(l >= r);
                break;
            default:
                if (!isNegated) {
                    return operands.get(0).execute(scope, functions);
                } else {
                    result.setValue(operands.get(0).execute(scope, functions).isTrue());
                }
        }
        return result;
    }
}
