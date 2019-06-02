package module

import model.data.TextPos
import model.data.Token
import model.data.TokenType

class ScannerTest extends GroovyTestCase {
    void testNumber() {
        String number = "12345";
        Reader reader = new StringReader(number);
        Source source = new Source(reader);
        Scanner scanner = new Scanner(source);
        scanner.nextToken();
        Token expected = new Token(TokenType.NUMBER, number, new Integer(12345), new TextPos(0, 0));

        assertEquals(expected.getTokenType(), scanner.getActualToken().getTokenType());
        assertEquals(expected.getLexem(), scanner.getActualToken().getLexem());
    }

    void testExpressionWithComment() {
        String expression = "i = 1 + 3 / 2; //comment \n i = 2;"
        Reader reader = new StringReader(expression)
        Source source = new Source(reader)
        Scanner scanner = new Scanner(source)
        scanner.nextToken()

        assertEquals(TokenType.IDENTIFIER, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.EQUAL, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.NUMBER, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.PLUS, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.NUMBER, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.SLASH, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.NUMBER, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.SEMICOLON, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.IDENTIFIER, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.EQUAL, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.NUMBER, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.SEMICOLON, scanner.getActualToken().getTokenType())
    }

    void testDeclarationAndAssignment() {
        String declaration = "string s; s = \"test\""
        Reader reader = new StringReader(declaration)
        Source source = new Source(reader)
        Scanner scanner = new Scanner(source)
        scanner.nextToken()

        assertEquals(TokenType.STRING, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.IDENTIFIER, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.SEMICOLON, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.IDENTIFIER, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.EQUAL, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.LETTERING, scanner.getActualToken().getTokenType())
    }

    void testMethodCall() {
        String methodCall = "e.setParam(\"value\", 4);"
        Reader reader = new StringReader(methodCall)
        Source source = new Source(reader)
        Scanner scanner = new Scanner(source)
        scanner.nextToken()

        assertEquals(TokenType.IDENTIFIER, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.DOT, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.IDENTIFIER, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.LEFT_PAREN, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.LETTERING, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.COMMA, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.NUMBER, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.RIGHT_PAREN, scanner.getActualToken().getTokenType())
        scanner.nextToken()
        assertEquals(TokenType.SEMICOLON, scanner.getActualToken().getTokenType())
    }

    void testProgram() {
        ArrayList<Token> tokenList = new ArrayList<Token>();
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

        Scanner scanner = new Scanner("Tests\\scanner_test1.txt");
        for (Token t : tokenList) {
            scanner.nextToken();
            assertEquals(t.tokenType, scanner.getActualToken().tokenType)
            assertEquals(t.lexem, scanner.getActualToken().lexem)
            assertEquals(t.tokenPos, scanner.getActualToken().tokenPos)
        }
    }
}