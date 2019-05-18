package model;

import model.data.TokenType;

public class Variable extends Node {
    private String name;
    private Node value;
    private TokenType varType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getValue() {
        return value;
    }

    public void setValue(Node value) {
        this.value = value;
    }

    public void setVarType(TokenType varType) {
        this.varType = varType;
    }

    public TokenType getVarType() {
        return varType;
    }

    @Override
    public Type getType() {
        return Type.Variable;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }
        builder.append("Variable: ")
                .append(name)
                .append(", ");
        if(varType != null)
            builder.append(varType.toString());

        System.out.println(builder.toString());
    }
}
