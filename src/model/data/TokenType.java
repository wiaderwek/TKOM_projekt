package model.data;

public enum TokenType {
    //jednoznakowe tokeny
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, SLASH,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, STAR,

    //jedno lub dwuznakowe tokeny
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    //literaly
    IDENTIFIER, LETTERING, NUMBER,

    //slowa kluczowe
    SONG, EFFECT, EFFECTS, AND, OR, IF,
    ELSE, FOR, TO, STEP, STRING, INT, RETURN,
    FUNC, PRINT, VOID,

    EOF;
}
