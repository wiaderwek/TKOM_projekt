package model;

public class PrintStatement extends Node {
    private Node text;

    public Node getText() {
        return text;
    }

    public void setText(Node text) {
        this.text = text;
    }

    @Override
    public Type getType() {
        return Type.PrintStatement;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("PRINT: ");
        System.out.println(builder.toString());

        text.print(tab+4);
    }
}
