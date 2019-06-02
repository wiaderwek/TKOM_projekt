package module;

import execute.FunctionEx;
import model.Program;

import java.util.List;

public class Interpreter {
    private static Scanner scanner;
    private static Parser parser;
    private static SemCheck semCheck;
    private static Executor executor;

    public static void exec(final Source source) {
        scanner = new Scanner(source);
        parser = new Parser(scanner);
        Program program = null;
        try {
            System.out.println("==========================PARSER==========================");
            program = parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        program.print(0);
        semCheck = new SemCheck();
        executor = new Executor();
        try {
            System.out.println("==========================SEM_CHECK========================");
            final List<FunctionEx> functionExes = semCheck.checkProgram(program);
            System.out.println("==========================EXECUTING========================");
            executor.execute(functionExes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
