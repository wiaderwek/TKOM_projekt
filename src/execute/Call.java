package execute;

import java.util.LinkedList;
import java.util.List;

public class Call extends Instruction implements Assignable, ExpressionOperand {
    String name;
    private List<Assignable> arguments =  new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addArgument(Assignable argument) {
        arguments.add(argument);
    }
}
