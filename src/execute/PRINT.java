package execute;

import java.util.Map;

public class PRINT extends Instruction {
    private Assignable text;

    public Assignable getText() {
        return text;
    }

    public void setText(Assignable text) {
        this.text = text;
    }

    @Override
    public Value execute(Scope scope, Map<String, FunctionEx> functions) {
        System.out.println("PRINT: " + text.execute(scope, functions).getValue());
        return null;
    }
}
