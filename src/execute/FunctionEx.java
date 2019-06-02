package execute;

import model.Function;

import java.util.List;
import java.util.Map;

public class FunctionEx extends Block {
    private Function function;
    private String name;

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Value execute(Scope scope, Map<String, FunctionEx> functions, final List<Value> arguments) {
        int varIdx = 0;

        for (final Value argument : arguments) {
            this.scope.addVariable(argument, this.scope.getVarName(varIdx++));
        }
        Value result;
        for (final Instruction instruction : instructions) {
            result = instruction.execute(this.scope, functions);
            if (instruction instanceof Return ||  (result != null && result.isReturn())) {
                result.setReturn(true);
                return  result;
            }
        }

        return null;
    }
}
