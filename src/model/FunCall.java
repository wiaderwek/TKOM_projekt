package model;

import java.util.LinkedList;
import java.util.List;

public class FunCall extends Node{
    private String name;
    private List<Node> arguments = new LinkedList<Node>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getArguments() {
        return arguments;
    }

    public void addArgument(Node argument) {
        arguments.add(argument);
    }

    @Override
    public Type getType() {
        return Type.FunCall;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("FunCall: ")
                .append(name)
                .append(", arguments: ");

        System.out.println(builder.toString());

        for(Node arg : arguments) {
            arg.print(tab+4);
        }
    }
}
