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
    private static int t = 1;
    private static int f = 0;

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
        Value l = new Value();
        Value r = new Value();
        result.setBoolean(true);
        if (operation != null) {
            switch (operation) {
                case OR:
                    for (final ConditionOperand operand : operands) {
                        if (operand.execute(scope, functions).isTrue()) {
                            result.setValue(t);
                            return result;
                        }
                    }
                    result.setValue(f);
                    return result;
                case AND:
                    for (final ConditionOperand operand : operands) {
                        if (!operand.execute(scope, functions).isTrue()) {
                            result.setValue(f);
                            return result;
                        }
                    }
                    result.setValue(t);
                    return result;
                case EQUAL_EQUAL:
                    l.setValue(operands.get(0).execute(scope, functions).getValue());
                    r.setValue(operands.get(1).execute(scope, functions).getValue());
                    if (l.isBoolean() && r.isBoolean()) {
                        result.setValue(l.isTrue() == r.isTrue() ? t : f);
                    } else if (!l.isBoolean() && !r.isBoolean()) {
                        result.setValue(l.getValue() == r.getValue() ? t : f);
                    }
                    //result.setTrue(l.getValue() == r.getValue());
                    break;
                case BANG_EQUAL:
                    l.setValue(operands.get(0).execute(scope, functions).getValue());
                    r.setValue(operands.get(1).execute(scope, functions).getValue());
                    if (l.isBoolean() && r.isBoolean()) {
                        result.setValue(l.isTrue() != r.isTrue() ? t : f);
                    } else if (!l.isBoolean() && !r.isBoolean()) {
                        result.setValue(l.getValue() != r.getValue() ? t : f);
                    }
                    break;
                case LESS:
                    l.setValue(operands.get(0).execute(scope, functions).getValue());
                    r.setValue(operands.get(1).execute(scope, functions).getValue());
                    result.setValue((int) l.getValue() < (int) r.getValue() ? t : f);
                    break;
                case LESS_EQUAL:
                    l.setValue(operands.get(0).execute(scope, functions).getValue());
                    r.setValue(operands.get(1).execute(scope, functions).getValue());
                    result.setValue((int) l.getValue() <= (int) r.getValue() ? t : f);
                    break;
                case GREATER:
                    l.setValue(operands.get(0).execute(scope, functions).getValue());
                    r.setValue(operands.get(1).execute(scope, functions).getValue());
                    result.setValue((int) l.getValue() > (int) r.getValue() ? t : f);
                    break;
                case GREATER_EQUAL:
                    l.setValue(operands.get(0).execute(scope, functions).getValue());
                    r.setValue(operands.get(1).execute(scope, functions).getValue());
                    result.setValue((int) l.getValue() > (int) r.getValue() ? t : f);
                    break;
                default:
                    if (!isNegated) {
                        return operands.get(0).execute(scope, functions);
                    } else {
                        result.setValue(operands.get(0).execute(scope, functions).isTrue());
                    }
            }
        } else {
            if (operands.get(0).execute(scope, functions).isTrue()) {
                result.setValue(operands.get(0).execute(scope, functions).getValue());
                return result;
            } else {
                result.setValue(operands.get(0).execute(scope, functions).getValue());
                return result;
            }
        }
        return result;
    }
}
