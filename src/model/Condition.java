package model;

import model.data.TokenType;

import java.util.LinkedList;
import java.util.List;

public class Condition extends Node {
    private boolean isNegated = false;
    private TokenType operator;

    private List<Node> operands = new LinkedList<Node>();

    public boolean isNegated() {
        return isNegated;
    }

    public void setNegated(boolean negated) {
        isNegated = negated;
    }

    public TokenType getOperator() {
        return operator;
    }

    public void setOperator(TokenType operator) {
        this.operator = operator;
    }

    public void addOperand(Node operand) {
        operands.add(operand);
    }

    public List<Node> getOperands() {
        return operands;
    }

    @Override
    public Type getType() {
        return Type.Condition;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("Condition: ");

        if(isNegated)
            builder.append("! ");

        System.out.println(builder);

        operands.get(0).print(tab+4);
        if(operator != null) {
            StringBuilder builderOperations = new StringBuilder();
            for(int j = 0; j < tab+4; ++j) {
                builderOperations.append(" ");
            }
            builderOperations.append(operator.toString());
            System.out.println(builderOperations.toString());
            operands.get(1).print(tab + 4);
        }
    }
}
