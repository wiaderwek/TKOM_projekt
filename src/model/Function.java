package model;

import model.data.Token;
import model.data.TokenType;

import java.util.LinkedList;
import java.util.List;

public class Function {
    private String name;
    private TokenType retType = TokenType.VOID;
    private List<Node> parameters = new LinkedList<Node>();
    private StatementBlock statementBlock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TokenType getRetType() {
        return retType;
    }

    public void setRetType(TokenType retType) {
        this.retType = retType;
    }

    public List<Node> getParameters() {
        return parameters;
    }

    public void setParameters(List<Node> parameters) {
        this.parameters = parameters;
    }

    public StatementBlock getStatementBlock() {
        return statementBlock;
    }

    public void setStatementBlock(StatementBlock statementBlock) {
        this.statementBlock = statementBlock;
    }

    public void print(int tab) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab; ++i) {
            builder.append(" ");
        }

        builder.append("Function: ")
                .append(retType.toString())
                .append(" ")
                .append(name);
        System.out.println(builder.toString());

        for(Node arg : parameters) {
            arg.print(tab+4);
        }

        statementBlock.print(tab+4);
    }
}
