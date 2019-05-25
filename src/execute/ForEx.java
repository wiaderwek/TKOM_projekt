package execute;

public class ForEx extends Instruction {
    private Instruction from;
    private Assignable to;
    private int step;
    private Block block;

    public Instruction getFrom() {
        return from;
    }

    public void setFrom(Instruction from) {
        this.from = from;
    }

    public Assignable getTo() {
        return to;
    }

    public void setTo(Assignable to) {
        this.to = to;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
