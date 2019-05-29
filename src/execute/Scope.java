package execute;

import model.Variable;

import java.lang.Object;
import java.util.*;

public class Scope {
    private Scope parentScope;
    private Map<String, Value> variables = new HashMap<>();
    private List<Variable> varOrder = new ArrayList<>();

    public boolean addVariable(final Variable variable) {
        if(variables.containsKey(variable.getName())) {
            return false;
        } else {
            variables.put(variable.getName(), new Value());
            varOrder.add(variable);
            return true;
        }
    }

    public void addVariable(final Value value, final String name) {
        variables.put(name, value);
    }

    public String getVarName(int index) {
        return varOrder.get(index).getName();
    }

    public Variable getVariableByName(String name) {
        if(hasVariable(name)) {
            for(Variable variable : varOrder) {
                if(variable.getName().equals(name)) {
                    return variable;
                }
            }
            return parentScope.getVariableByName(name);
        }
        return null;
    }

    public Value getValue(String name) {
        if(variables.containsKey(name)) {
            return variables.get(name);
        } else {
            return null;
        }
    }

    public Map<String, Value> getVariables() {
        return variables;
    }

    public void setParentScope(Scope scope) {
        this.parentScope = scope;
    }

    public void setVarOrder(final List<Variable> varOrder) {
        this.varOrder = varOrder;
    }

    public List<Variable> getVarOrder() {
        return varOrder;
    }

    public boolean hasVariable(final String variableName) {
        if(!variables.containsKey(variableName) && parentScope != null) {
            return parentScope.hasVariable(variableName);
        }

        return variables.containsKey(variableName);
    }

    public void setVariableValue(String name, Value value) {
        Value var = variables.get(name);
        var.setValue(value.getValue());
        var.setCalculated(true);
    }

    public void setVariable(String name, Value value) {
        variables.put(name, value);
    }

    public Scope getScope(Scope parentScope) {
        final Scope scope = new Scope();
        scope.setParentScope(parentScope);
        scope.setVarOrder(parentScope.getVarOrder());
        variables.forEach((name, variable) -> scope.setVariable(name, variable));
        parentScope.getVariables().forEach((name, variable) -> scope.setVariable(name, variable));
        return scope;
    }
}
