import execute.FunctionEx;
import model.Program;
import model.data.TextPos;
import model.data.Token;
import model.data.TokenType;
import module.Executor;
import module.Parser;
import module.Scanner;
import module.SemCheck;

import java.util.ArrayList;
import java.util.List;

public class IntegrationTest {
    private static Scanner scanner;
    private static List<Token> tokenList;

    public static void main(String[] args) {
        Scanner scanner = new Scanner("Tests\\scanner_test1.txt");
        Parser parser = new Parser(scanner);
        SemCheck semCheck = new SemCheck();
        try {
            Program program = parser.parse();
            program.print(0);
            List<FunctionEx> functionExes = semCheck.checkProgram(program);
            Executor executor = new Executor();
            executor.execute(functionExes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
