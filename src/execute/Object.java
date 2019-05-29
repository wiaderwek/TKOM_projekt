package execute;

import model.data.TokenType;

import java.util.List;

public abstract class Object extends java.lang.Object {
    String name;
    public abstract boolean hasMethod(String name);
    public abstract TokenType getRetType(String name);
    public abstract TokenType getObjType();
    public abstract List<TokenType> getArgumentsType(String name);
    public abstract Value executeMethod(String name, List<Value> arguments);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public abstract boolean equals(java.lang.Object o);
}
