package model;

import model.Node;

public class IntLiteral extends Node {
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private int value;

    @Override
    public Type getType() {
        return Type.IntLiteral;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("IntLiteral: ")
                .append(value);

        System.out.println(builder.toString());
    }
}
