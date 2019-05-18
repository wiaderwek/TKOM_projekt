package model;

import model.data.TokenType;

public class Declaration extends Node {
    TokenType tokenType;
    private String name;

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Type getType() {
        return Type.Declaration;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("Declaration: ")
                .append(tokenType.toString())
                .append(" ")
                .append(name);

        System.out.println(builder.toString());
    }
}
