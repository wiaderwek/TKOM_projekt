package execute;

import model.data.TokenType;

import java.util.LinkedList;
import java.util.List;

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
}
