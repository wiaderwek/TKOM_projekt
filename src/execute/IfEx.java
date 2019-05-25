package execute;

public class IfEx extends Instruction {
    private ConditionEx condition;
    private Block trueBlock;
    private Block elseBlock;

    public ConditionEx getCondition() {
        return condition;
    }

    public void setCondition(ConditionEx condition) {
        this.condition = condition;
    }

    public Block getTrueBlock() {
        return trueBlock;
    }

    public void setTrueBlock(Block trueBlock) {
        this.trueBlock = trueBlock;
    }

    public Block getElseBlock() {
        return elseBlock;
    }

    public void setElseBlock(Block elseBlock) {
        this.elseBlock = elseBlock;
    }
}
