package execute;

import model.data.TokenType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
}
