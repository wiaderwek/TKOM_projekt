package model;

public class Assignment extends Node {
    private Variable variable;
    private Node value;

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public Node getValue() {
        return value;
    }

    public void setValue(Node value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return Type.Assignment;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("Assignment: ");
        System.out.println(builder.toString());
        if(variable != null)
            variable.print(tab+4);
        value.print(tab + 4);
    }
}
