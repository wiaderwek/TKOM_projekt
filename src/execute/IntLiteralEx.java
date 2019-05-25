package execute;

public class IntLiteralEx implements ExpressionOperand, ConditionOperand{
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean isTrue() {
        return value > 0;
    }
}
