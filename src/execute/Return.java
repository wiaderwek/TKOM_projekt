package execute;

public class Return extends Instruction {
    private Assignable value;

    public void setValue(final Assignable value) {
        this.value = value;
    }
}
