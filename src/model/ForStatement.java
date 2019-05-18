package model;

public class ForStatement extends Node{
    private Assignment from;
    private Node to;
    private int step = 1;
    private StatementBlock statementBlock;

    public Assignment getFrom() {
        return from;
    }

    public void setFrom(Assignment from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public StatementBlock getStatementBlock() {
        return statementBlock;
    }

    public void setStatementBlock(StatementBlock statementBlock) {
        this.statementBlock = statementBlock;
    }

    @Override
    public Type getType() {
        return Type.ForStatement;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        addTabToBuilder(tab, builder);

        builder.append("ForStatement: ")
                .append("step: " + step);

        System.out.println(builder.toString());

        addTabToBuilder(tab, builder);
        builder.append("from: ");
        System.out.println(builder.toString());
        from.print(tab+4);

        addTabToBuilder(tab, builder);
        builder.append("to: ");
        System.out.println(builder.toString());
        to.print(tab+4);
        addTabToBuilder(tab, builder);
        builder.append("block: ");
        System.out.println(builder.toString());
        statementBlock.print(tab+4);
    }

    private void addTabToBuilder(int tab, StringBuilder builder) {
        builder.delete(0, builder.length());
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }
    }
}
