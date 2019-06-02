package execute;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Block extends Instruction {
    protected Scope scope = new Scope();
    protected List<Instruction> instructions = new LinkedList<>();

    public Scope getScope() {
        return scope;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void addInstruction(final Instruction instruction) {
        instructions.add(instruction);
    }

    @Override
    public Value execute(Scope scope, Map<String, FunctionEx> functions) {
        final Scope newScope = this.scope.getScope(scope);
        for(final Instruction instruction : instructions) {
            final Value result = instruction.execute(newScope, functions);
            if(instruction instanceof Return || (result != null && result.isReturn())) {
                result.setReturn(true);
                return result;
            }
        }
        return null;
    }
}
