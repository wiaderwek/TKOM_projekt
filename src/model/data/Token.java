package model.data;

import java.lang.Object;

public class Token {
    private final TokenType tokenType;
    private final String lexem;
    private final Object literal;
    private final TextPos tokenPos;

    public Token(TokenType tokenType, String lexem, Object literal, TextPos tokenPos) {
        this.tokenType = tokenType;
        this.lexem = lexem;
        this.literal = literal;
        this.tokenPos = tokenPos;
    }

    public Token(TokenType tokenType, String lexem, TextPos tokenPos) {
        this.tokenType = tokenType;
        this.lexem = lexem;
        this.literal = null;
        this.tokenPos = tokenPos;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getLexem() {
        return lexem;
    }

    public TextPos getTokenPos() {
        return tokenPos;
    }

    public boolean equal(Token token) {
        if(this.tokenType != token.getTokenType()) {
            return false;
        } else if (!this.lexem.equals(token.getLexem())) {
            System.out.println("lex");
            return false;
        } else if (!this.tokenPos.equals(token.getTokenPos())) {
            System.out.println("pos");
            return false;
        } else {
            return true;
        }
    }
}
