package module

import model.Assignment
import model.Condition
import model.Declaration
import model.Expression
import model.ForStatement
import model.FunCall
import model.Function
import model.IfStatement
import model.MethodCall
import model.PrintStatement
import model.Program
import model.StatementBlock
import model.Variable
import model.data.TokenType

class ParserTest extends GroovyTestCase {
    private parser;
    void testDeclaration() {
        Reader reader = new StringReader("FUNC void func() {int i;}")
        parser = new Parser(new Scanner(new Source(reader)))
        Program program = parser.parse()
        Function function = program.getFunctions().get(0)
        assertEquals(function.statementBlock.instructions.get(0).class, Declaration.class)
    }

    void testAssignments() {
        Reader reader = new StringReader("FUNC void func() {int i; int j; i = 1; j = 0;}")
        parser = new Parser(new Scanner(new Source(reader)))
        Program program = parser.parse()
        Function function = program.getFunctions().get(0)
        assertEquals(function.statementBlock.instructions.get(0).class, Declaration.class)
        assertEquals(function.statementBlock.instructions.get(1).class, Declaration.class)
        assertEquals(function.statementBlock.instructions.get(2).class, Assignment.class)
        assertEquals(function.statementBlock.instructions.get(3).class, Assignment.class)
    }

    void testIf() {
        Reader reader = new StringReader("FUNC void func(int i) {IF (i == 0 || i == 1) { PRINT(i);} ELSE { func(i - 1); }}")
        parser = new Parser(new Scanner(new Source(reader)))
        Program program = parser.parse()
        Function function = program.getFunctions().get(0)
        assertEquals(function.parameters.size(), 1)
        assertEquals(function.statementBlock.instructions.get(0).class, IfStatement.class)

        IfStatement ifStatement = function.statementBlock.instructions.get(0)
        assertEquals(ifStatement.condition.operands.size(), 2)

        Condition condition = ifStatement.condition
        assertEquals(condition.operator, TokenType.OR)

        assertEquals(ifStatement.hasElseBlock(), true)
        assertEquals(ifStatement.trueBlock.instructions.get(0).class, PrintStatement.class)
        assertEquals(ifStatement.elseBlock.instructions.get(0).class, FunCall.class)

        FunCall funCall = ifStatement.elseBlock.instructions.get(0)
        assertEquals(funCall.arguments.get(0).class, Expression.class)

        Expression expression = funCall.arguments.get(0)
        assertEquals(expression.getOperands().size(), 2)
        assertEquals(expression.getOperations().size(), 1)
        assertEquals(expression.getOperations().get(0), TokenType.MINUS)
    }

    void testFor() {
        Reader reader = new StringReader("FUNC void func(int i) {int j; FOR j = 0 TO i STEP 2 {PRINT(j);}}")
        parser = new Parser(new Scanner(new Source(reader)))
        Program program = parser.parse()
        Function function = program.getFunctions().get(0)
        assertEquals(function.parameters.size(), 1)
        assertEquals(function.statementBlock.instructions.size(), 2)
        assertEquals(function.statementBlock.instructions.get(0).class, Declaration.class)
        assertEquals(function.statementBlock.instructions.get(1).class, ForStatement.class)

        ForStatement forStatement = function.statementBlock.instructions.get(1)
        assertEquals(forStatement.to.class, Expression.class)
        assertEquals(forStatement.step, 2)
        assertEquals(forStatement.statementBlock.instructions.size(), 1)
        assertEquals(forStatement.statementBlock.instructions.get(0).class, PrintStatement.class)
    }

    void testMethodCall() {
        Reader reader = new StringReader("FUNC void func() {Effect e; e.setType(\"Extend\");}")
        parser = new Parser(new Scanner(new Source(reader)))
        Program program = parser.parse()
        Function function = program.getFunctions().get(0)

        assertEquals(function.statementBlock.instructions.size(), 2)
        assertEquals(function.statementBlock.instructions.get(0).class, Declaration.class)
        assertEquals(function.statementBlock.instructions.get(1).class, MethodCall.class)

        MethodCall methodCall = function.statementBlock.instructions.get(1)
        assertEquals(methodCall.name, "setType")
        assertEquals(methodCall.arguments.size(), 1)
        assertEquals(methodCall.arguments.get(0).class, Expression.class)
    }

    void testMultipleFunctionDefinitions() {
        Reader reader = new StringReader("FUNC void func1(){} FUNC void func2(){} FUNC void func3(){}")
        parser = new Parser(new Scanner(new Source(reader)))
        Program program = parser.parse()

        assertEquals(program.functions.size(), 3)
    }

    void testExpression() {
        Reader reader = new StringReader("FUNC void func1(int i){ i = 1 + 2 * 3 - 4;}")
        parser = new Parser(new Scanner(new Source(reader)))
        Program program = parser.parse()
        Function function = program.getFunctions().get(0)

        assertEquals(function.statementBlock.instructions.size(), 1)
        assertEquals(function.statementBlock.instructions.get(0).class, Assignment.class)

        Assignment assignment = function.statementBlock.instructions.get(0)
        Expression expression = (Expression) assignment.value
        assertEquals(expression.operands.size(), 3)
        assertEquals(expression.operations.size(), 2)
        assertEquals(expression.operations.get(0), TokenType.PLUS)
        assertEquals(expression.operations.get(1), TokenType.MINUS)

        Expression multiplication = (Expression) expression.operands.get(1)
        assertEquals(multiplication.operands.size(), 2)
        assertEquals(multiplication.operations.size(), 1)
        assertEquals(multiplication.operations.get(0), TokenType.STAR)
    }
}
