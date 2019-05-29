package execute;

import model.data.TokenType;

import java.lang.Object;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExpressionEx implements Assignable, ExpressionOperand {
    private List<TokenType> operations = new LinkedList<>();
    private List<ExpressionOperand> operands = new LinkedList<>();

    public void addOperand(final ExpressionOperand expressionOperand) {
        operands.add(expressionOperand);
    }

    public List<TokenType> getOperations() {
        return operations;
    }

    public void setOperations(List<TokenType> operations) {
        this.operations = operations;
    }

    public List<ExpressionOperand> getOperands() {
        return operands;
    }

    public void setOperands(List<ExpressionOperand> operands) {
        this.operands = operands;
    }

    @Override
    public Value execute(Scope scope, Map<String, FunctionEx> functions) {
        final Value value = new Value();
        value.setValue(operands.get(0).execute(scope, functions).getValue());
        if(operations.size() == 0) {
            return value;
        } else {
            int index = 1;
            for (final TokenType operation : operations) {
                final ExpressionOperand operand = operands.get(index);
                ++index;
                switch (operation) {
                    case PLUS:
                        value.plus(operand.execute(scope, functions));
                        break;
                    case MINUS:
                        value.minus(operand.execute(scope, functions));
                        break;
                    case SLASH:
                        value.divide(operand.execute(scope, functions));
                        break;
                    case STAR:
                        value.multiply(operand.execute(scope, functions));
                        break;
                    default:
                        break;
                }
            }
        }
        return value;
    }
}
