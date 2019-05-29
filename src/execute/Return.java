package execute;

import java.util.Map;

public class Return extends Instruction {
    private Assignable value;

    public void setValue(final Assignable value) {
        this.value = value;
    }

    @Override
    public Value execute(Scope scope, Map<String, FunctionEx> functions) {
        return  value.execute(scope, functions);
    }
}
