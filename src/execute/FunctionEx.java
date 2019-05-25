package execute;

import model.Function;

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

}
