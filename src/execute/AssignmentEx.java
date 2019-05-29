package execute;

import model.Variable;
import model.data.TokenType;

import java.util.Map;

public class AssignmentEx extends Instruction {
    private String name;
    private TokenType type;
    private Assignable value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public Assignable getValue() {
        return value;
    }

    public void setValue(Assignable value) {
        this.value = value;
    }


    @Override
    public Value execute(Scope scope, Map<String, FunctionEx> functions) {
        scope.setVariableValue(name, value.execute(scope, functions));
        return null;
    }
}
