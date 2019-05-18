package model;

import model.data.TokenType;

import java.util.LinkedList;
import java.util.List;

public class Expression extends Node {
    private List<TokenType> operations = new LinkedList<TokenType>();
    private List<Node> operands = new LinkedList<Node>();

    public void addOperation(TokenType operation) {
        operations.add(operation);
    }

    public List<TokenType> getOperations() {
        return operations;
    }

    public void addOperand(Node operand) {
        operands.add(operand);
    }

    public List<Node> getOperands() {
        return operands;
    }

    @Override
    public Type getType() {
        return Type.Expression;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("Expression: ");
        System.out.println(builder.toString());

        operands.get(0).print(tab+4);
        for(int i = 0; i < operations.size(); ++i) {
            StringBuilder builderOperations = new StringBuilder();
            for(int j = 0; j < tab+4; ++j) {
                builderOperations.append(" ");
            }
            builderOperations.append(operations.get(i).toString());
            System.out.println(builderOperations.toString());
            operands.get(i+1).print(tab+4);
        }
    }
}
