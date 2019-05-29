package execute;

import model.data.TokenType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MethodCallEx extends Instruction implements Assignable, ExpressionOperand {
    private String name;
    private Object calee;
    private TokenType caleeType;
    private List<Assignable> arguments =  new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCalee() {
        return calee;
    }

    public void setCalee(Object calee) {
        this.calee = calee;
    }

    public TokenType getCaleeType() {
        return caleeType;
    }

    public void setCaleeType(TokenType caleeType) {
        this.caleeType = caleeType;
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
                java.lang.Object obj = argument.execute(scope, functions).getValue();
                value.setValue(obj);
                value.setCalculated(true);
            } else {
                value.setCalculated(false);
                value.setInstructionValue(argument);
            }
            concreteArguments.add(value);
        }
        if(calee.hasMethod(name)) {
            System.out.println("[DEBUG] method call: \"" + name + "\" executing...");
            calee = (Object) scope.getValue(calee.getName()).getValue();
            Value returnVal = calee.executeMethod(name, concreteArguments);
            Value newObjVal = new Value(calee);
            newObjVal.setCalculated(true);
            scope.setVariable(calee.getName(), newObjVal);
            return returnVal;
        }
        return null;
    }

    private boolean isValue(final Assignable assignable) {
        if(assignable instanceof ExpressionEx) {
            final ExpressionEx expressionEx = (ExpressionEx) assignable;
            if(expressionEx.getOperations().size() == 0) {
                final ExpressionEx childExpr = (ExpressionEx) expressionEx.getOperands().get(0);
                if (childExpr.getOperations().size() == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
