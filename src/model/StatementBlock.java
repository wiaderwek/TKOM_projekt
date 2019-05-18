package model;

import java.util.LinkedList;
import java.util.List;

public class StatementBlock extends Node {
    private List<Node> instructions = new LinkedList<Node>();

    public List<Node> getInstructions() {
        return instructions;
    }

    public void addInstruction(Node instruction) {
        instructions.add(instruction);
    }

    @Override
    public Type getType() {
        return Type.StatementBlock;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("Statement block: ");
        System.out.println(builder.toString());

        for(Node node : instructions) {
            node.print(tab + 4);
        }
    }
}
