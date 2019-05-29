package execute;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Call extends Instruction implements Assignable, ExpressionOperand {
    String name;
    private List<Assignable> arguments;

    public Call() {
        this.arguments =  new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addArgument(Assignable argument) {
        arguments.add(argument);
    }

    @Override
    public Value execute(Scope scope, Map<String, FunctionEx> functions) {
        final List<Value> concreteArguments = new LinkedList<>();
        for (final Assignable argument : arguments) {
            final Value value = new Value();
            if( argument instanceof VariableEx || isValue(argument)) {
                value.setCalculated(true);
                value.setValue(argument.execute(scope, functions).getValue());
            } else {
                value.setCalculated(false);
                value.setInstructionValue(argument);
            }
            concreteArguments.add(value);
        }
        if(functions.containsKey(name)) {
            System.out.println("[DEBUG] function: \"" + name + "\" executing...");
            return functions.get(name).execute(scope, functions, concreteArguments);
        }
        return null;
    }

    private boolean isValue(final Assignable assignable) {
        if(assignable instanceof ExpressionEx) {
            final ExpressionEx expressionEx = (ExpressionEx) assignable;
            if(expressionEx.getOperands().size() == 0) {
                final ExpressionEx childExpr = (ExpressionEx) expressionEx.getOperands().get(0);
                if (childExpr.getOperations().size() == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
