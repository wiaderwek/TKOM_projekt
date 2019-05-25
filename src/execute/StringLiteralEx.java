package execute;

public class StringLiteralEx implements ExpressionOperand, ConditionOperand {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean isTrue() {
        return false;
    }
}
