package execute;

public class VariableEx implements ExpressionOperand, Assignable, ConditionOperand {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isTrue() {
        return false;
    }
}
