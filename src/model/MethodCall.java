package model;

import java.util.LinkedList;
import java.util.List;

public class MethodCall extends Node {
    private String calee;
    private String name;
    private List<Node> arguments = new LinkedList<Node>();

    public String getCalee() {
        return calee;
    }

    public void setCalee(String calee) {
        this.calee = calee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getArguments() {
        return arguments;
    }

    public void setArguments(List<Node> arguments) {
        this.arguments = arguments;
    }

    public void addArgument(Node argument) {
        arguments.add(argument);
    }

    @Override
    public Type getType() {
        return Type.MethodCall;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("Method call: ")
                .append(name)
                .append(" by ")
                .append(calee)
                .append(", arguments: ");

        System.out.println(builder.toString());

        for(Node node : arguments) {
            node.print(tab + 4);
        }
    }
}
