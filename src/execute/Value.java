package execute;

import model.Expression;

import java.lang.Object;

public class Value {
    private boolean isReturn = false;
    private Object value = new Object();
    private boolean isCalculated;
    private Assignable instructionValue = null;
    private boolean isBoolean = false;

    public Value() {}

    public Value(Object value) {
        this.value = value;
    }

    public boolean isTrue() {
        return ((int) value) > 0;
    }

    public boolean isBoolean() {
        return isBoolean;
    }

    public void setBoolean(boolean aBoolean) {
        isBoolean = aBoolean;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isCalculated() {
        return isCalculated;
    }

    public void setCalculated(boolean calculated) {
        isCalculated = calculated;
    }

    public Assignable getInstructionValue() {
        return instructionValue;
    }

    public void setInstructionValue(Assignable instructionValue) {
        this.instructionValue = instructionValue;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }

    public void plus(Value value) {
        if (value.getValue() instanceof Integer && this.value instanceof Integer) {
            this.value = (Integer) ((Integer) this.value) + ((Integer) value.getValue());
        } else if (value.getValue() instanceof Integer && this.value instanceof String) {
            this.value = (String) ((String) this.value) + ((Integer) value.getValue());
        } else if (value.value instanceof String) {
            this.value = (String) ((String) this.value) + ((String) value.getValue());
        }
    }

    public void minus(Value value) {
        if (value.getValue() instanceof Integer) {
            this.value =
                    ((Integer) this.value)
                    - ((Integer) value.getValue());
        } else {
            throw new IllegalArgumentException("Illegal operation! Cannot substract " + value.getValue().getClass() +
                    " from " + this.value.getClass());
        }
    }

    public void divide(Value value) {
        if (value.getValue() instanceof Integer) {
            this.value = ((Integer) this.value) / ((Integer) value.getValue());
        }
    }

    public void multiply(Value value) {
        if (value.getValue() instanceof Integer) {
            this.value = ((Integer) this.value) * ((Integer) value.getValue());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(!(obj instanceof Value)) {
            return false;
        }

        Value value = (Value) obj;
        if(this.isBoolean != value.isBoolean) {
            return false;
        } else if(!this.value.equals(value.getValue())) {
            return false;
        } else {
            return true;
        }
    }
}
