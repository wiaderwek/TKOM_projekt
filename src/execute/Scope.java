package execute;

import model.Variable;

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

    public Map<String, Value> getVariables() {
        return variables;
    }

    public void setParentScope(Scope scope) {
        this.parentScope = scope;
    }

    public boolean hasVariable(final String variableName) {
        if(!variables.containsKey(variableName) && parentScope != null) {
            return parentScope.hasVariable(variableName);
        }

        return variables.containsKey(variableName);
    }
}
