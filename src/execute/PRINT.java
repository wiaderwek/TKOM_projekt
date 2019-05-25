package execute;

public class PRINT extends Instruction {
    private Assignable text;

    public Assignable getText() {
        return text;
    }

    public void setText(Assignable text) {
        this.text = text;
    }
}
