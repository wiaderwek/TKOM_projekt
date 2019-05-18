package model;

import model.Node;
import model.data.TokenType;

public class Object extends Node {
    //można podzielić na 3 klasy
    private String name;
    private TokenType objType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TokenType getObjType() {
        return objType;
    }

    public void setObjType(TokenType objType) {
        this.objType = objType;
    }

    @Override
    public Type getType() {
        return Type.Object;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("Object: ")
                .append(name)
                .append(", ")
                .append(objType.toString());

        System.out.println(builder.toString());
    }
}
