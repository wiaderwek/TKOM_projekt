import execute.FunctionEx;
import model.Program;
import model.data.TextPos;
import model.data.Token;
import model.data.TokenType;

import java.util.ArrayList;
import java.util.List;

public class ScannerTest {
    private static Scanner scanner;
    private static List<Token> tokenList;

    private static boolean scannerTest1() {
        tokenList = new ArrayList<Token>();
        tokenList.add(new Token(TokenType.FUNC, new String("FUNC"), new TextPos(1,1)));
        tokenList.add(new Token(TokenType.VOID, new String("void"), new TextPos(1,6)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("printEffects"), new TextPos(1,11)));
        tokenList.add(new Token(TokenType.LEFT_PAREN, new String("("), new TextPos(1,23)));
        tokenList.add(new Token(TokenType.EFFECTS, new String("Effects"), new TextPos(1,24)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("e"), new TextPos(1,32)));
        tokenList.add(new Token(TokenType.RIGHT_PAREN, new String(")"), new TextPos(1,33)));
        tokenList.add(new Token(TokenType.LEFT_BRACE, new String("{"), new TextPos(1,35)));

        tokenList.add(new Token(TokenType.INT, new String("int"), new TextPos(2,9)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("i"), new TextPos(2,13)));
        tokenList.add(new Token(TokenType.SEMICOLON, new String(";"), new TextPos(2,14)));

        tokenList.add(new Token(TokenType.FOR, new String("FOR"), new TextPos(3,9)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("i"), new TextPos(3,13)));
        tokenList.add(new Token(TokenType.EQUAL, new String("="), new TextPos(3,15)));
        tokenList.add(new Token(TokenType.NUMBER, new String("0"), new TextPos(3,17)));
        tokenList.add(new Token(TokenType.TO, new String("TO"), new TextPos(3,19)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("e"), new TextPos(3,22)));
        tokenList.add(new Token(TokenType.DOT, new String("."), new TextPos(3,23)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("getCount"), new TextPos(3,24)));
        tokenList.add(new Token(TokenType.LEFT_PAREN, new String("("), new TextPos(3,32)));
        tokenList.add(new Token(TokenType.RIGHT_PAREN, new String(")"), new TextPos(3,33)));
        tokenList.add(new Token(TokenType.STEP, new String("STEP"), new TextPos(3,35)));
        tokenList.add(new Token(TokenType.NUMBER, new String("2"), new TextPos(3,40)));
        tokenList.add(new Token(TokenType.LEFT_BRACE, new String("{"), new TextPos(3,42)));

        tokenList.add(new Token(TokenType.EFFECT, new String("Effect"), new TextPos(4,17)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("ef"), new TextPos(4,24)));
        tokenList.add(new Token(TokenType.SEMICOLON, new String(";"), new TextPos(4,26)));

        tokenList.add(new Token(TokenType.IDENTIFIER, new String("ef"), new TextPos(5,17)));
        tokenList.add(new Token(TokenType.EQUAL, new String("="), new TextPos(5,20)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("e"), new TextPos(5,22)));
        tokenList.add(new Token(TokenType.DOT, new String("."), new TextPos(5,23)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("getAt"), new TextPos(5,24)));
        tokenList.add(new Token(TokenType.LEFT_PAREN, new String("("), new TextPos(5,29)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("i"), new TextPos(5,30)));
        tokenList.add(new Token(TokenType.RIGHT_PAREN, new String(")"), new TextPos(5,31)));
        tokenList.add(new Token(TokenType.SEMICOLON, new String(";"), new TextPos(5,32)));

        tokenList.add(new Token(TokenType.PRINT, new String("PRINT"), new TextPos(6,17)));
        tokenList.add(new Token(TokenType.LEFT_PAREN, new String("("), new TextPos(6,22)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("ef"), new TextPos(6,23)));
        tokenList.add(new Token(TokenType.DOT, new String("."), new TextPos(6,25)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("getParamValue"), new TextPos(6,26)));
        tokenList.add(new Token(TokenType.LEFT_PAREN, new String("("), new TextPos(6,39)));
        tokenList.add(new Token(TokenType.LETTERING, new String("value"), new String(),  new TextPos(6,40)));
        tokenList.add(new Token(TokenType.RIGHT_PAREN, new String(")"), new TextPos(6,47)));
        tokenList.add(new Token(TokenType.RIGHT_PAREN, new String(")"), new TextPos(6,48)));
        tokenList.add(new Token(TokenType.SEMICOLON, new String(";"), new TextPos(6,49)));

        tokenList.add(new Token(TokenType.RIGHT_BRACE, new String("}"), new TextPos(7,9)));

        tokenList.add(new Token(TokenType.RIGHT_BRACE, new String("}"), new TextPos(8,1)));

        tokenList.add(new Token(TokenType.FUNC, new String("FUNC"), new TextPos(10,1)));
        tokenList.add(new Token(TokenType.VOID, new String("void"), new TextPos(10,6)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("main"), new TextPos(10,11)));
        tokenList.add(new Token(TokenType.LEFT_PAREN, new String("("), new TextPos(10,15)));
        tokenList.add(new Token(TokenType.RIGHT_PAREN, new String(")"), new TextPos(10,16)));
        tokenList.add(new Token(TokenType.LEFT_BRACE, new String("{"), new TextPos(10,18)));

        tokenList.add(new Token(TokenType.EFFECTS, new String("Effects"), new TextPos(11,5)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("e"), new TextPos(11,13)));
        tokenList.add(new Token(TokenType.SEMICOLON, new String(";"), new TextPos(11,14)));

        tokenList.add(new Token(TokenType.EFFECT, new String("Effect"), new TextPos(12,5)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("ex"), new TextPos(12,12)));
        tokenList.add(new Token(TokenType.SEMICOLON, new String(";"), new TextPos(12,14)));

        tokenList.add(new Token(TokenType.IDENTIFIER, new String("ex"), new TextPos(13,5)));
        tokenList.add(new Token(TokenType.DOT, new String("."), new TextPos(13,7)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("setType"), new TextPos(13,8)));
        tokenList.add(new Token(TokenType.LEFT_PAREN, new String("("), new TextPos(13,15)));
        tokenList.add(new Token(TokenType.LETTERING, new String("Extend"), new String(), new TextPos(13,16)));
        tokenList.add(new Token(TokenType.RIGHT_PAREN, new String(")"), new TextPos(13,24)));
        tokenList.add(new Token(TokenType.SEMICOLON, new String(";"), new TextPos(13,25)));

        tokenList.add(new Token(TokenType.IDENTIFIER, new String("ex"), new TextPos(14,5)));
        tokenList.add(new Token(TokenType.DOT, new String("."), new TextPos(14,7)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("setParam"), new TextPos(14,8)));
        tokenList.add(new Token(TokenType.LEFT_PAREN, new String("("), new TextPos(14,16)));
        tokenList.add(new Token(TokenType.LETTERING, new String("value"), new String(), new TextPos(14,17)));
        tokenList.add(new Token(TokenType.COMMA, new String(","), new TextPos(14,24)));
        tokenList.add(new Token(TokenType.NUMBER, new String("4"), new TextPos(14,26)));
        tokenList.add(new Token(TokenType.RIGHT_PAREN, new String(")"), new TextPos(14,27)));
        tokenList.add(new Token(TokenType.SEMICOLON, new String(";"), new TextPos(14,28)));

        tokenList.add(new Token(TokenType.IDENTIFIER, new String("e"), new TextPos(16,5)));
        tokenList.add(new Token(TokenType.DOT, new String("."), new TextPos(16,6)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("add"), new TextPos(16,7)));
        tokenList.add(new Token(TokenType.LEFT_PAREN, new String("("), new TextPos(16,10)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("ex"), new TextPos(16,11)));
        tokenList.add(new Token(TokenType.RIGHT_PAREN, new String(")"), new TextPos(16,13)));
        tokenList.add(new Token(TokenType.SEMICOLON, new String(";"), new TextPos(16,14)));

        tokenList.add(new Token(TokenType.IDENTIFIER, new String("printEffects"), new TextPos(18,5)));
        tokenList.add(new Token(TokenType.LEFT_PAREN, new String("("), new TextPos(18,17)));
        tokenList.add(new Token(TokenType.IDENTIFIER, new String("e"), new TextPos(18,18)));
        tokenList.add(new Token(TokenType.RIGHT_PAREN, new String(")"), new TextPos(18,19)));
        tokenList.add(new Token(TokenType.SEMICOLON, new String(";"), new TextPos(18,20)));

        tokenList.add(new Token(TokenType.RIGHT_BRACE, new String("}"), new TextPos(19,1)));

        scanner = new Scanner("Tests\\scanner_test1.txt");
        Token token;
        for (Token t : tokenList) {
            scanner.nextToken();
            token = scanner.getActualToken();
            if (!token.equal(t)) {
                System.out.println(token.getLexem() + " " + token.getTokenPos());
                return false;
            }
        }
        scanner.nextToken();
        token = scanner.getActualToken();
        if(token.getTokenType() != TokenType.EOF) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {

        String result = new String("FAILED");
        if(scannerTest1()) {
            result =  new String("PASSED");
        }

        System.out.println("ScannerTest1: " + result);

        System.out.println("Parser test:");
        Scanner scanner = new Scanner("Tests\\scanner_test1.txt");
        Parser parser = new Parser(scanner);
        SemCheck semCheck = new SemCheck();
        try {
            Program program = parser.parse();
            program.print(0);
            List<FunctionEx> functionExes = semCheck.checkProgram(program);
            if(functionExes.size() == 2) {
                System.out.println("Good");
            }
            Executor executor = new Executor();
            executor.execute(functionExes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
