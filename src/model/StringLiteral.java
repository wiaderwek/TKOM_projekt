package model;

public class StringLiteral extends Node {
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String value;

    @Override
    public Type getType() {
        return Type.StringLiteral;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("String literal: ");
        builder.append(value);
        System.out.println(builder.toString());
    }
}
