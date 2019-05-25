package execute;

import model.data.TokenType;

import java.util.List;

public abstract class Object extends java.lang.Object {
    public abstract boolean hasMethod(String name);
    public abstract TokenType getRetType(String name);
    public abstract TokenType getObjType();
    public abstract List<TokenType> getArgumentsType(String name);

    @Override
    public abstract boolean equals(java.lang.Object o);
}
