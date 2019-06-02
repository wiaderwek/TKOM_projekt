package module;

import model.*;
import model.data.Token;
import model.data.TokenType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class Parser {


    private Scanner scanner;
    private Program program;
    private Token previousToken;

    public Parser(String fileName) {
        scanner = new Scanner(fileName);
    }

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    public Program parse() throws Exception {
        resetBufferedToken();
        program = new Program();
        Function lastParsedFunction = null;
        while((lastParsedFunction = parseFunction()) != null) {
            program.addFunctions(lastParsedFunction);
        }

        return program;
    }

    private void resetBufferedToken() {
        previousToken = null;
    }

    private boolean isBufferedToken() {
        return previousToken != null;
    }

    private Token accept(final TokenType... tokenTypes) throws Exception {
        Token token;
        if (isBufferedToken()) {
            token = previousToken;
            resetBufferedToken();
        } else {
            scanner.nextToken();
            token = scanner.getActualToken();
        }

        if(isAcceptable(token, tokenTypes)) {
            return token;
        } else {
            final StringBuilder sb = new StringBuilder()
                    .append("Unexpected token: ")
                    .append(token.getLexem())
                    .append(" in line ")
                    .append(token.getTokenPos().getLineNumber())
                    .append(" char: ")
                    .append(token.getTokenPos().getCharNumber())
                    .append(" Expected: ");
            for(final TokenType tokenType : tokenTypes) {
                sb.append(tokenType + " ");
            }
            System.out.println(sb.toString());
            throw new Exception(sb.toString());
        }
    }

    private boolean checkNextTokens(final TokenType... tokenTypes) throws Exception {
        if(!isBufferedToken()) {
            scanner.nextToken();
            previousToken = scanner.getActualToken();
        }

        return isAcceptable(previousToken, tokenTypes);
    }

    private boolean isAcceptable(final Token token, TokenType... tokenTypes) {
        for(final TokenType tokenType : tokenTypes) {
            if(tokenType.equals(token.getTokenType())) {
                return true;
            }
        }

        return false;
    }

    private Function parseFunction() throws Exception {
        System.out.println("Start parsing function...");
        final Function function = new Function();
        final Token startToken = accept(TokenType.FUNC, TokenType.EOF);
        if(!TokenType.FUNC.equals(startToken.getTokenType())) {
            return null;
        }

        final Token returnType = accept(TokenType.VOID, TokenType.INT, TokenType.STRING);
        function.setRetType(returnType.getTokenType());

        final Token functionName = accept(TokenType.IDENTIFIER);
        function.setName(functionName.getLexem());
        function.setParameters(parseParameters());
        function.setStatementBlock(parseStatementBlock(returnType.getTokenType()));
        return function;
    }

    private List<Node> parseParameters() throws Exception {
        List<Node> parameters = new LinkedList<Node>();
        accept(TokenType.LEFT_PAREN);
        Token tmp = accept(TokenType.RIGHT_PAREN, TokenType.INT, TokenType.STRING, TokenType.EFFECT, TokenType.EFFECTS, TokenType.SONG);
        if(!tmp.getTokenType().equals(TokenType.RIGHT_PAREN)) {
            Variable argument = new Variable();
            argument.setVarType(tmp.getTokenType());
            Token argName = accept(TokenType.IDENTIFIER);
            argument.setName(argName.getLexem());
            parameters.add(argument);
            while (true) {
                tmp = accept(TokenType.COMMA, TokenType.RIGHT_PAREN);
                if(tmp.getTokenType().equals(TokenType.RIGHT_PAREN)) {
                    break;
                }
                Variable nextArgument = new Variable();
                tmp = accept(TokenType.INT, TokenType.STRING, TokenType.EFFECT, TokenType.EFFECTS, TokenType.SONG);
                nextArgument.setVarType(tmp.getTokenType());
                argName = accept(TokenType.IDENTIFIER);
                nextArgument.setName(argName.getLexem());
                parameters.add(nextArgument);
            }
        }

        return parameters;
    }

    private StatementBlock parseStatementBlock(TokenType returnType) throws Exception {
        System.out.println("Parsing Statement block...");
        final StatementBlock block = new StatementBlock();
        accept(TokenType.LEFT_BRACE);
        while(true) {
            if(!checkNextTokens(TokenType.IF, TokenType.FOR, TokenType.RETURN, TokenType.IDENTIFIER,
                    TokenType.INT, TokenType.STRING, TokenType.EFFECT, TokenType.EFFECTS, TokenType.SONG, TokenType.PRINT)) {
                break;
            }
            final Token tmp = previousToken;

            switch (tmp.getTokenType()) {
                case IF:
                    block.addInstruction(parseIfStatement(returnType));
                    break;
                case FOR:
                    block.addInstruction(parseForStatement(returnType));
                    break;
                case RETURN:
                    block.addInstruction(parseReturnStatement(returnType));
                    break;
                case IDENTIFIER:
                    block.addInstruction(parseAssignStatementtOrFunnCall());
                    break;
                case INT:
                    block.addInstruction(parseDeclaration());
                    break;
                case STRING:
                    block.addInstruction(parseDeclaration());
                    break;
                case EFFECT:
                    block.addInstruction(parseDeclaration());
                    break;
                case EFFECTS:
                    block.addInstruction(parseDeclaration());
                    break;
                case SONG:
                    block.addInstruction(parseDeclaration());
                    break;
                case PRINT:
                    block.addInstruction(parsePrintStatement());
                    break;
                default:
                    break;

            }
        }
        accept(TokenType.RIGHT_BRACE);
        return block;
    }

    private Node parseIfStatement(TokenType returnType) throws Exception {
        System.out.println("Parse if statement...");
        final IfStatement ifStatement = new IfStatement();
        accept(TokenType.IF);
        accept(TokenType.LEFT_PAREN);
        ifStatement.setCondition(parseCondition());
        accept(TokenType.RIGHT_PAREN);
        ifStatement.setTrueBlock(parseStatementBlock(returnType));
        if(checkNextTokens(TokenType.ELSE)) {
            accept(TokenType.ELSE);
            ifStatement.setElseBlock(parseStatementBlock(returnType));
        }

        return ifStatement;
    }

    private Condition parseCondition() throws Exception {
        System.out.println("Parse condition...");
        final Condition condition = new Condition();
        condition.addOperand(parseAndCondition());
        while (checkNextTokens(TokenType.OR)) {
            accept(TokenType.OR);
            condition.setOperator(TokenType.OR);
            condition.addOperand(parseAndCondition());
        }
        return condition;
    }

    private Condition parseAndCondition() throws Exception {
        System.out.println("Parse and condition...");
        final Condition andCondtion = new Condition();
        andCondtion.addOperand(parseEqualityCondition());
        while (checkNextTokens(TokenType.AND)) {
            accept(TokenType.AND);
            andCondtion.setOperator(TokenType.AND);
            andCondtion.addOperand(parseEqualityCondition());
        }

        return andCondtion;
    }

    private Node parseEqualityCondition() throws Exception {
        System.out.println("Parse equality condition...");
        final Condition equalityCondition = new Condition();
        equalityCondition.addOperand(parseRelationalCondition());
        if(checkNextTokens(TokenType.EQUAL_EQUAL, TokenType.BANG_EQUAL)) {
            final Token token = accept(TokenType.EQUAL_EQUAL, TokenType.BANG_EQUAL);
            equalityCondition.setOperator(token.getTokenType());
            equalityCondition.addOperand(parseRelationalCondition());
        }
        return equalityCondition;
    }

    private Node parseRelationalCondition() throws Exception {
        System.out.println("Parse relational condition...");
        final Condition relationalCondition = new Condition();
        relationalCondition.addOperand(parsePrimaryCondition());
        if(checkNextTokens(TokenType.LESS, TokenType.LESS_EQUAL, TokenType.GREATER, TokenType.GREATER_EQUAL)) {
            final Token token = accept(TokenType.LESS, TokenType.LESS_EQUAL, TokenType.GREATER, TokenType.GREATER_EQUAL);
            relationalCondition.setOperator(token.getTokenType());
            relationalCondition.addOperand(parsePrimaryCondition());
        }

        return relationalCondition;
    }

    private Node parsePrimaryCondition() throws Exception {
        System.out.println("Parse primary condition...");
        final Condition primaryCondition = new Condition();
        if(checkNextTokens(TokenType.BANG)) {
            accept(TokenType.BANG);
            primaryCondition.setNegated(true);
        }
        if(checkNextTokens(TokenType.LEFT_PAREN)) {
            accept(TokenType.LEFT_PAREN);
            primaryCondition.addOperand(parseCondition());
            accept(TokenType.RIGHT_PAREN);
        } else if(checkNextTokens(TokenType.IDENTIFIER)) {
            primaryCondition.addOperand(parseVariable(null));
        } else if(checkNextTokens(TokenType.LETTERING)){
            primaryCondition.addOperand(parseStringLiteral());
        } else {
            primaryCondition.addOperand(parseIntLiteral());
        }
        return primaryCondition;
    }

    private Node parseForStatement(TokenType returnType) throws Exception {
        System.out.println("Parse for statement...");
        final ForStatement forStatement = new ForStatement();
        accept(TokenType.FOR);

        Assignment assignment = new Assignment();
        Variable variable = new Variable();
        Token varName = accept(TokenType.IDENTIFIER);
        variable.setName(varName.getLexem());
        assignment.setVariable(variable);
        accept(TokenType.EQUAL);
        assignment.setValue(parseAssignable());
        forStatement.setFrom(assignment);

        accept(TokenType.TO);
        forStatement.setTo(parseAssignable());
        if(checkNextTokens(TokenType.STEP)) {
            accept(TokenType.STEP);
            forStatement.setStep(parseIntLiteral().getValue());
        }
        forStatement.setStatementBlock(parseStatementBlock(returnType));

        return forStatement;

    }

    private Node parseReturnStatement(TokenType returnType) throws Exception {
        System.out.println("Parse return statement...");
        final ReturnStatement returnStatement = new ReturnStatement();
        accept(TokenType.RETURN);
        if(returnType != TokenType.VOID){
            returnStatement.setReturnValue(parseAssignable());
        } else {
            returnStatement.setReturnValue(null);
        }
        accept(TokenType.SEMICOLON);
        return returnStatement;
    }

    private Node parseAssignStatementtOrFunnCall() throws Exception {
        System.out.println("Parse assignment or function call...");
        final Token tmp = accept(TokenType.IDENTIFIER);
        Node instruction = parseFunnOrMethodCall(tmp.getLexem());
        if(instruction == null) {
           final Assignment assignment = new Assignment();
           final Variable variable = new Variable();
           variable.setName(tmp.getLexem());
           assignment.setVariable(variable);
           accept(TokenType.EQUAL);
           assignment.setValue(parseAssignable());
           instruction = assignment;
        }
        accept(TokenType.SEMICOLON);
        return instruction;
    }

    private Declaration parseDeclaration() throws Exception {
        System.out.println("Parse declaration...");
        Declaration declaration = new Declaration();
        declaration.setTokenType(previousToken.getTokenType());
        resetBufferedToken();
        Token name = accept(TokenType.IDENTIFIER);
        declaration.setName(name.getLexem());
        accept(TokenType.SEMICOLON);

        return declaration;
    }

    private Node parseFunnOrMethodCall(final String name) throws Exception {
        if(checkNextTokens(TokenType.DOT)) {
            return parseMethodCall(name);
        } else if (checkNextTokens(TokenType.LEFT_PAREN)) {
            return parseFunCall(name);
        } else {
            return null;
        }

    }

    private Node parseMethodCall(String calee) throws Exception {
        System.out.println("Parse method call...");
        final MethodCall methodCall = new MethodCall();
        methodCall.setCalee(calee);
        accept(TokenType.DOT);
        Token methodName = accept(TokenType.IDENTIFIER);
        FunCall funCall = (FunCall) parseFunCall(methodName.getLexem());
        methodCall.setArguments(funCall.getArguments());
        methodCall.setName(methodName.getLexem());

        return methodCall;
    }

    private Node parseFunCall(String name) throws Exception {
        System.out.println("Parse function call...");
        final FunCall funCall = new FunCall();
        funCall.setName(name);
        accept(TokenType.LEFT_PAREN);
        if(checkNextTokens(TokenType.RIGHT_PAREN)) {
            accept(TokenType.RIGHT_PAREN);
        } else {
            while (true) {
                funCall.addArgument(parseAssignable());
                if(checkNextTokens(TokenType.RIGHT_PAREN)) {
                    accept(TokenType.RIGHT_PAREN);
                    break;
                } else if(checkNextTokens(TokenType.COMMA)) {
                    accept(TokenType.COMMA);
                } else {
                    accept(TokenType.RIGHT_PAREN, TokenType.COMMA);
                }
            }
        }

        return funCall;
    }

    private Node parseAssignable() throws Exception{
        System.out.println("Parse assignable...");
        Node assignable;
        if(checkNextTokens(TokenType.IDENTIFIER)) {
            final Token tmp = accept(TokenType.IDENTIFIER);
            assignable = parseFunnOrMethodCall(tmp.getLexem());
            if(assignable == null) {
                if(checkNextTokens(TokenType.SEMICOLON, TokenType.RIGHT_PAREN)) {
                    assignable = new Variable();
                    ((Variable) assignable).setName(tmp.getLexem());
                    return assignable;
                }
                assignable = parseExpression(tmp);
            }
        } else {
            assignable = parseExpression(null);
        }
        return assignable;
    }

    private Node parseExpression(final Token firstToken) throws Exception {
        System.out.println("Parse expression...");
        final Expression expression = new Expression();
        expression.addOperand(parseMultiplicativeExpression(firstToken));
        while(checkNextTokens(TokenType.PLUS, TokenType.MINUS)) {
            final Token token = accept(TokenType.MINUS, TokenType.PLUS);
            System.out.println(token.getTokenType().toString());
            expression.addOperation(token.getTokenType());
            expression.addOperand(parseMultiplicativeExpression(null));
        }
        return expression;
    }

    private Node parseMultiplicativeExpression(Token firstToken) throws Exception {
        System.out.println("Parse multiplicative expression...");
        final Expression expression = new Expression();
        expression.addOperand(parsePrimaryExpression(firstToken));
        while(checkNextTokens(TokenType.STAR, TokenType.SLASH)) {
            final Token token = accept(TokenType.STAR, TokenType.SLASH);
            System.out.println(token.getTokenType().toString());
            expression.addOperation(token.getTokenType());
            expression.addOperand(parsePrimaryExpression(null));
        }
        return expression;
    }

    private Node parsePrimaryExpression(Token firstToken) throws Exception {
        System.out.println("Parse prmiary expression...");
        Node expression = null;
        if(firstToken != null) {
            expression = parseVariable(firstToken);
            return expression;
        }
        if (checkNextTokens(TokenType.LEFT_PAREN)) {
            accept(TokenType.LEFT_PAREN);
            expression = parseExpression(null);
            accept(TokenType.RIGHT_PAREN);
        } else if(checkNextTokens(TokenType.IDENTIFIER)) {
            expression = parseVariable(null);
        } else if(checkNextTokens(TokenType.NUMBER)){
            expression = parseIntLiteral();
        } else {
            expression = parseStringLiteral();
        }
        return expression;
    }

    private Node parseStringLiteral() throws Exception {
        System.out.println("Parse string literal...");
        final StringLiteral variable = new StringLiteral();
        Token tmp = accept(TokenType.LETTERING);
        variable.setValue(tmp.getLexem());

        return variable;
    }

    private IntLiteral parseIntLiteral() throws Exception {
        System.out.println("Parse int literal...");
        final IntLiteral variable = new IntLiteral();
        boolean isNegative = false;
        if (checkNextTokens(TokenType.MINUS)) {
            accept(TokenType.MINUS);
            isNegative = true;
        }
        final Token token = accept(TokenType.NUMBER);
        int value = Integer.valueOf(token.getLexem());
        if (isNegative)
            value *= -1;
        variable.setValue(value);

        return variable;
    }

    private Variable parseVariable(Token firstToken) throws Exception {
        System.out.println("Parse variable...");
        final Variable variable = new Variable();
        if(firstToken == null) {
            final Token token = accept(TokenType.IDENTIFIER);
            variable.setName(token.getLexem());
        } else {
            variable.setName(firstToken.getLexem());
        }
        return variable;
    }

    private PrintStatement parsePrintStatement() throws Exception {
        accept(TokenType.PRINT);
        accept(TokenType.LEFT_PAREN);
        PrintStatement printStatement = new PrintStatement();
        printStatement.setText(parseAssignable());
        accept(TokenType.RIGHT_PAREN);
        accept(TokenType.SEMICOLON);

        return printStatement;
    }

}
