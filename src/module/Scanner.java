package module;

import model.data.TextPos;
import model.data.Token;
import model.data.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Scanner {
    private Source source;
    private Token actualToken;
    private String lexem;
    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<String, TokenType>();
        keywords.put("IF",     TokenType.IF);
        keywords.put("ELSE",   TokenType.ELSE);
        keywords.put("FOR",    TokenType.FOR);
        keywords.put("TO",     TokenType.TO);
        keywords.put("STEP",   TokenType.STEP);
        keywords.put("FUNC",   TokenType.FUNC);
        keywords.put("RETURN", TokenType.RETURN);
        keywords.put("PRINT",  TokenType.PRINT);
        keywords.put("int",    TokenType.INT);
        keywords.put("string", TokenType.STRING);
        keywords.put("void",   TokenType.VOID);
        keywords.put("Song",   TokenType.SONG);
        keywords.put("Effect", TokenType.EFFECT);
        keywords.put("Effects",TokenType.EFFECTS);
    }

    public Scanner(Source source) {
        this.source = source;
    }

    public Scanner(String fileName) {
        this.source = new Source(fileName);
    }

    public Token getActualToken() {
        return actualToken;
    }

    private void setActualToken(TokenType tokenType, String text, Object literal, TextPos textPos) {
        this.actualToken = new Token(tokenType, text, literal, textPos);
    }

    private void setActualToken(TokenType tokenType, String text,TextPos textPos) {
        setActualToken(tokenType, text, null, textPos);
    }

    private boolean skipWhiteSpacesAndComments() {
        while(true) {
            char c = source.getCurrentChar();
            switch (c) {
                case '/':
                    TextPos startPos = source.getCurrentPos();
                    source.nextChar();
                    if(source.getCurrentChar() == '/') {
                        skipLine();
                    } else {
                        setActualToken(TokenType.SLASH, Character.toString(c), startPos);
                        return true;
                    }
                    break;
                case ' ':
                    source.nextChar();
                    break;
                case '\t':
                    source.nextChar();
                    break;
                case '\n':
                    source.nextLine();
                    break;
                case '\r':
                    source.nextChar();
                    break;
                default:
                    return false;
            }
        }
    }

    private void skipLine() {
        while(source.getCurrentChar()!='\n') {
            source.nextChar();
        }
        source.nextLine();
    }

    public void nextToken() {
        if(skipWhiteSpacesAndComments())
            return;
        char c = source.getCurrentChar();
        lexem = Character.toString(c);
        TextPos startPos = new TextPos(source.getCurrentPos());
            switch (c) {
                case '(': setActualToken(TokenType.LEFT_PAREN, lexem, startPos);       source.nextChar(); break;
                case ')': setActualToken(TokenType.RIGHT_PAREN, lexem, startPos);      source.nextChar(); break;
                case '{': setActualToken(TokenType.LEFT_BRACE, lexem, startPos);       source.nextChar(); break;
                case '}': setActualToken(TokenType.RIGHT_BRACE, lexem, startPos);      source.nextChar(); break;
                case ',': setActualToken(TokenType.COMMA, lexem, startPos);            source.nextChar(); break;
                case '.': setActualToken(TokenType.DOT, lexem, startPos);              source.nextChar(); break;
                case '-': setActualToken(TokenType.MINUS, lexem, startPos);            source.nextChar(); break;
                case '+': setActualToken(TokenType.PLUS, lexem, startPos);             source.nextChar(); break;
                case ';': setActualToken(TokenType.SEMICOLON, lexem, startPos);        source.nextChar(); break;
                case '*': setActualToken(TokenType.STAR, lexem, startPos);             source.nextChar(); break;
                case '!':
                    source.nextChar();
                    if(source.getCurrentChar() == '=') {
                        lexem += Character.toString(source.getCurrentChar());
                        setActualToken(TokenType.BANG_EQUAL, lexem, startPos );        source.nextChar(); break;
                    } else {
                        setActualToken(TokenType.BANG, lexem, startPos );                                 break;
                    }
                case '=':
                    source.nextChar();
                    if(source.getCurrentChar() == '=') {
                        lexem += Character.toString(source.getCurrentChar());
                        setActualToken(TokenType.EQUAL_EQUAL, lexem, startPos );       source.nextChar(); break;
                    } else {
                        setActualToken(TokenType.EQUAL, lexem, startPos );                                break;
                    }
                case '<':
                    source.nextChar();
                    if(source.getCurrentChar() == '=') {
                        lexem += Character.toString(source.getCurrentChar());
                        setActualToken(TokenType.LESS_EQUAL, lexem, startPos );        source.nextChar(); break;
                    } else {
                        setActualToken(TokenType.LESS, lexem, startPos );                                 break;
                    }
                case '>':
                    source.nextChar();
                    if(source.getCurrentChar() == '=') {
                        lexem += Character.toString(source.getCurrentChar());
                        setActualToken(TokenType.GREATER_EQUAL, lexem, startPos );     source.nextChar(); break;
                    } else {
                        setActualToken(TokenType.GREATER, lexem, startPos );                              break;
                    }
                case '"':
                    source.nextChar();
                    string(startPos);                                                                      break;
                case '„':
                    source.nextChar();
                    string(startPos);                                                                     break;
                case '|':
                    source.nextChar();
                    if(source.getCurrentChar() == '|') {
                        lexem += Character.toString(source.getCurrentChar());
                        setActualToken(TokenType.OR, lexem, startPos );                source.nextChar(); break;
                    } else {
                        source.error(source.getCurrentPos(), "Unexpected character.");            break;
                    }
                case '&':
                    source.nextChar();
                    if(source.getCurrentChar() == '&') {
                        lexem += Character.toString(source.getCurrentChar());
                        setActualToken(TokenType.AND, lexem, startPos );                source.nextChar(); break;
                    } else {
                        source.error(source.getCurrentPos(), "Unexpected character.");            break;
                    }
                case '\0':
                    setActualToken(TokenType.EOF, lexem, startPos);                                        break;
                default:
                    if(isDigit(c)) {
                        number(startPos);
                    } else if(isAlpha(c)) {
                        identifier(startPos);
                    } else if(source.isAtEnd()) {
                        setActualToken(TokenType.EOF, lexem, startPos);
                    } else {
                        source.error(startPos, "Unexpected character.");
                    }
            }
    }

    private void string(TextPos textPos){
        lexem = new String();
        while(source.getCurrentChar() != '"') {
            if( source.isAtEnd()) {
                source.error(source.getCurrentPos(), "Unterminated string.");
                setActualToken(TokenType.EOF, Character.toString('\0'), source.getCurrentPos());
                return;
            }
            lexem += Character.toString(source.getCurrentChar());
            source.nextChar();
        }
        setActualToken(TokenType.LETTERING, lexem, lexem, textPos);
        source.nextChar();
    }

    private void number(TextPos textPos) {
        lexem = new String();
        while(isDigit(source.getCurrentChar())) {
            lexem += Character.toString(source.getCurrentChar());
            source.nextChar();
        }
        Integer num = Integer.parseInt(lexem);
        setActualToken(TokenType.NUMBER, lexem, num, textPos);
    }

    private void identifier(TextPos textPos){
        lexem = new String();
        while (isAlphaNumeric(source.getCurrentChar())) {
            lexem += Character.toString(source.getCurrentChar());
            source.nextChar();
        }

        TokenType tokenType = keywords.get(lexem);
        if(tokenType != null) {
            setActualToken(tokenType, lexem, textPos);
        } else {
            setActualToken(TokenType.IDENTIFIER, lexem, textPos);
        }
    }

    private  boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

}
