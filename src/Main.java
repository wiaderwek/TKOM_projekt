import execute.FunctionEx;
import model.Program;
import module.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();
        interpreter.exec(new Source("Tests\\kod.txt"));
    }
}
