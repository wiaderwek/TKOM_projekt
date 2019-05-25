package execute;

import model.data.TokenType;

import java.util.LinkedList;
import java.util.List;

public class MethodCallEx extends Instruction implements Assignable, ExpressionOperand {
    private String name;
    private Object calee;
    private TokenType caleeType;
    private List<Assignable> arguments =  new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCalee() {
        return calee;
    }

    public void setCalee(Object calee) {
        this.calee = calee;
    }

    public TokenType getCaleeType() {
        return caleeType;
    }

    public void setCaleeType(TokenType caleeType) {
        this.caleeType = caleeType;
    }

    public void addArgument(Assignable argument) {
        arguments.add(argument);
    }
}
