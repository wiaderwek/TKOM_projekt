package model;

public class IfStatement extends Node {
    private Condition condition;
    private StatementBlock trueBlock;
    private StatementBlock elseBlock = null;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public StatementBlock getTrueBlock() {
        return trueBlock;
    }

    public void setTrueBlock(StatementBlock trueBlock) {
        this.trueBlock = trueBlock;
    }

    public StatementBlock getElseBlock() {
        return elseBlock;
    }

    public void setElseBlock(StatementBlock elseBlock) {
        this.elseBlock = elseBlock;
    }

    public boolean hasElseBlock() {
        if(elseBlock == null) {
            return false;
        }

        return true;
    }

    @Override
    public Type getType() {
        return Type.IfStatement;
    }

    @Override
    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("IfStatement: ");
        System.out.println(builder.toString());

        condition.print(tab+4);
        trueBlock.print(tab+4);
        elseBlock.print(tab+4);
    }
}
