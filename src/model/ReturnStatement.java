package model;

public class ReturnStatement extends Node {
    private Node returnValue;

    public Node getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Node returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public Type getType() {
        return Type.ReturnStatement;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("Return value: ");
        System.out.println(builder.toString());

        returnValue.print(tab+4);
    }
}
