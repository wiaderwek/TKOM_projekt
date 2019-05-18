package model;

public abstract class Node {
    protected Node parent;

    public enum Type {
        Assignment,
        Condition,
        Expression,
        ForStatement,
        FunCall,
        FunDefinition,
        IfCondition,
        IfStatement,
        IntLiteral,
        MethodCall,
        Object,
        Declaration,
        PrintStatement,
        Program,
        ReturnStatement,
        StatementBlock,
        StringLiteral,
        Variable
    }

    public abstract void print(int tab);
    public abstract Type getType();
}
