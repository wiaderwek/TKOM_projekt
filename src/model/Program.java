package model;

import java.util.LinkedList;
import java.util.List;

public class Program extends Node {
    private List<Function> functions = new LinkedList<Function>();

    public List<Function> getFunctions() {
        return functions;
    }

    public void addFunctions(Function function) {
        functions.add(function);
    }

    @Override
    public Type getType() {
        return Type.Program;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("Program: ");
        System.out.println(builder.toString());

        for(Function function : functions) {
            function.print(tab+4);
        }
    }
}
