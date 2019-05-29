import com.sun.org.apache.xerces.internal.xinclude.MultipleScopeNamespaceSupport;
import execute.*;
import model.*;
import model.data.TokenType;

import java.lang.Object;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SemCheck {
    private Map<String, FunctionEx> definedFunctions = new HashMap<>();
    private Program program;

    public List<FunctionEx> checkProgram(final Program program) throws Exception {
        definedFunctions.clear();
        this.program = program;

        scanDefinedFunctios();
        checkMain();

        final List<FunctionEx> functions = new LinkedList<>();
        for (final Function function : program.getFunctions()) {
            functions.add(checkFunction(function));
        }

        return functions;
    }

    private void scanDefinedFunctios() throws Exception {
        for(final Function function : program.getFunctions()) {
            scanDefinedFunction(function);
        }
    }

    private void scanDefinedFunction(final Function function) throws Exception {
        if(definedFunctions.containsKey(function.getName())) {
            throw new Exception("Redefined function: " + function.getName());
        }
        final FunctionEx functionEx = new FunctionEx();
        functionEx.setName(function.getName());
        for (final Node parameter : function.getParameters()) {
            if(!functionEx.getScope().addVariable((Variable) parameter)) {
                throw new Exception(new StringBuilder()
                        .append("Duplicated parameter: ")
                        .append(((Variable) parameter).getName())
                        .append(" in function ")
                        .append(function.getName()).toString());
            }
        }
        functionEx.setFunction(function);

        definedFunctions.put(function.getName(), functionEx);
    }

    private void checkMain() throws Exception {
        if (!definedFunctions.containsKey("main")) {
            throw new Exception("No \"main\" function defined");
        }
        if(definedFunctions.get("main").getScope().getVariables().size() != 0) {
            throw new Exception("\"main\" function should not have parameters");
        }
    }

    private FunctionEx checkFunction(Function function) throws Exception {
        final FunctionEx functionEx = definedFunctions.get(function.getName());
        TokenType returnedType = definedFunctions.get(function.getName()).getFunction().getRetType();
        functionEx.getInstructions().add(checkBlock(functionEx.getScope(), function.getStatementBlock(), returnedType));
        return functionEx;
    }

    private Block checkBlock(Scope scope, StatementBlock statementBlock, TokenType returnedType) throws Exception {
        final Block block = new Block();
        block.getScope().setParentScope(scope);
        for (final Node instruction : statementBlock.getInstructions()) {
            switch (instruction.getType()) {
                case Assignment:
                    final Assignment assignment = (Assignment) instruction;
                    block.addInstruction(checkAssignment(block.getScope(), assignment, null));
                    break;
                case ReturnStatement:
                    final ReturnStatement returnStatement = (ReturnStatement) instruction;
                    block.addInstruction(checkReturnStatement(block.getScope(), returnStatement, returnedType));
                    break;
                case FunCall:
                    final FunCall funCall = (FunCall) instruction;
                    block.addInstruction(checkFunCall(block.getScope(), funCall, null));
                    break;
                case IfStatement:
                    final IfStatement ifStatement = (IfStatement) instruction;
                    block.addInstruction(checkIfStatement(block.getScope(), ifStatement, returnedType));
                    break;
                case ForStatement:
                    final ForStatement forStatement = (ForStatement) instruction;
                    block.addInstruction(checkForStatement(block.getScope(), forStatement, returnedType));
                    break;
                case Declaration:
                    final Declaration declaration = (Declaration) instruction;
                    checkDeclaration(block.getScope(), declaration);
                    break;
                case MethodCall:
                    final MethodCall methodCall = (MethodCall) instruction;
                    block.addInstruction(checkMethodCall(block.getScope(), methodCall, null));
                    break;
                case PrintStatement:
                    final PrintStatement printStatement = (PrintStatement) instruction;
                    block.addInstruction(checkPrint(block.getScope(), printStatement));
                    break;
            }
        }
        return block;
    }

    private Instruction checkAssignment(final Scope scope, final Assignment assignment, final TokenType requiredType) throws Exception {
        final AssignmentEx assignmentEx = new AssignmentEx();
        final String variableName =  assignment.getVariable().getName();
        if(!scope.hasVariable(variableName)) {
            throw new Exception("Not decalred variable: " + assignment.getVariable().getName());
        }

        final TokenType type = scope.getVariableByName(variableName).getVarType();
        if(requiredType != null && type != requiredType) {
            throw new Exception("Can not iterate over [" + type + "] in FOR statement");
        }

        assignmentEx.setName(variableName);
        assignmentEx.setType(type);
        assignmentEx.setValue(checkAssignable(scope, assignment.getValue(), type));

        return assignmentEx;
    }

    private Assignable checkAssignable(final Scope scope, final Node assignable, final TokenType type) throws Exception {
        if(Node.Type.FunCall.equals(assignable.getType())) {
            return checkFunCall(scope, (FunCall) assignable, type);
        } else if (Node.Type.MethodCall.equals(assignable.getType())) {
            return checkMethodCall(scope, (MethodCall) assignable, type);
        } else if (Node.Type.Expression.equals(assignable.getType())) {
            return checkExpression(scope, (Expression) assignable, type);
        } else if (Node.Type.Variable.equals(assignable.getType())) {
            return checkVariable(scope, (Variable) assignable, type);
        } else {
            throw new Exception("Invalid assignable value");
        }
    }

    private ExpressionEx checkExpression(final Scope scope, final Expression expression, final TokenType type) throws Exception {
        final ExpressionEx expressionEx = new ExpressionEx();
        expressionEx.setOperations(expression.getOperations());
        switchForExpression(expression.getOperands().get(0), null, scope, expression, type, expressionEx);
        for (int i = 0; i < expression.getOperations().size(); ++i) {
            final Node operand = expression.getOperands().get(i+1);
            final TokenType operation = expression.getOperations().get(i);
            switchForExpression(operand, operation, scope, expression, type, expressionEx);

        }
        return expressionEx;
    }

    private void switchForExpression(Node operand, final TokenType operation, final Scope scope,
                                     final Expression expression, final TokenType type, ExpressionEx expressionEx) throws Exception {
        switch (operand.getType()) {
            case IntLiteral:
                checkIntLiteralInExpression(scope, (IntLiteral) operand, operation, expressionEx, type);
                break;

            case StringLiteral:
                checkStringLiteralInExpression(scope, (StringLiteral) operand, operation, expressionEx, type);
                break;

            case Expression:
                expressionEx.addOperand(checkExpression(scope, (Expression) operand, type));
                break;

            case Variable:
                checkVariablelInExpression(scope, (Variable) operand, operation, expressionEx, type);
                break;

            case FunCall:
                checkFunCallInExpression(scope, (FunCall) operand, operation, expressionEx, type);
                break;

            case MethodCall:
                checkMethodCallInExpression(scope, (MethodCall) operand, operation, expressionEx, type);
                break;

            default:
                break;
        }
    }

    private void checkIntLiteralInExpression(Scope scope, IntLiteral operand, TokenType operation, ExpressionEx expressionEx, TokenType type) throws Exception {
        if(type == TokenType.INT) {
            expressionEx.addOperand(checkIntLiteral(scope, operand));
        } else if (type == TokenType.STRING && operation == TokenType.PLUS) {
            expressionEx.addOperand(checkStringLiteral(scope, operand));
        } else {
            if( type == TokenType.STRING && operation != null) {
                throw new Exception("Incompatible operation! Cannot use [" + operation + "] to concatenate int with string");
            } else {
                throw new Exception("Incompatible types! Requierd [" + type + "]");
            }
        }
    }

    private void checkStringLiteralInExpression(Scope scope, StringLiteral operand, TokenType operation, ExpressionEx expressionEx, TokenType type) throws Exception {
        if (operation != null && operation != TokenType.PLUS && type == TokenType.STRING) {
            throw new Exception("Cannot use [" + operation + "] with string type");
        } else if(type == TokenType.STRING) {
            expressionEx.addOperand(checkStringLiteral(scope, operand));
        } else {
            throw new Exception("Incompatible types! Requierd [" + type + "]");
        }
    }

    private void checkVariablelInExpression(Scope scope, Variable operand, TokenType operation, ExpressionEx expressionEx, TokenType type) throws Exception {
        if(!(scope.hasVariable(operand.getName()))) {
            throw new Exception("Usage of undefined variable: " + operand.getName());
        }
        Variable scopeVariable = scope.getVariableByName(operand.getName());
        if(type == scopeVariable.getVarType()) {
            if (type == TokenType.STRING && operation != TokenType.PLUS) {
                throw new Exception("Incompatible operation! Cannot use [" + operation + "]  with " + type);
            } else if (type != TokenType.INT && operation != null) {
                throw new Exception("Incompatible operation! Cannot use [" + operation + "]  with " + type);
            } else {
                expressionEx.addOperand(checkVariable(scope, operand, type));
            }
        } else if (type == TokenType.STRING && operation == TokenType.PLUS && scopeVariable.getVarType() == TokenType.INT) {
            expressionEx.addOperand(checkVariable(scope, scopeVariable, type));
        } else {
            if( type == TokenType.STRING && scopeVariable.getVarType() == TokenType.INT && operation != null) {
                throw new Exception("Incompatible operation! Cannot use [" + operation + "] to concatenate int with string");
            } else {
                throw new Exception("Incompatible types! Requierd [" + type + "]");
            }
        }
    }

    private void checkFunCallInExpression(Scope scope, FunCall operand, TokenType operation, ExpressionEx expressionEx, TokenType type) throws Exception {
        if(definedFunctions.containsKey(operand.getName())) {
            throw new Exception("Undefined function: " + operand.getName());
        }
        final Function function = definedFunctions.get(operand.getName()).getFunction();
        if(function.getRetType() != type) {
            boolean isIntAddedToString = type == TokenType.STRING && function.getRetType() == TokenType.INT
                    && operation == TokenType.PLUS;
            if (isIntAddedToString) {
                expressionEx.addOperand(checkFunCall(scope, operand, type));
                return;
            } else {
                if(type == TokenType.STRING && function.getRetType() == TokenType.INT && operation != null) {
                    throw new Exception("Incompatible operation! Cannot use [" + operation + "] to concatenate int with string");
                } else {
                    throw new Exception("Incompatible types! Requierd [" + type + "]");
                }
            }
        }
        expressionEx.addOperand(checkFunCall(scope, operand, type));
    }

    private void checkMethodCallInExpression(Scope scope, MethodCall operand, TokenType operation, ExpressionEx expressionEx, TokenType type) throws Exception {
        if(!scope.hasVariable(operand.getCalee())) {
            throw new Exception("Undefined object: " + operand.getCalee());
        }

        execute.Object objCalle;
        Variable calee = scope.getVariableByName(operand.getCalee());
        if(calee.getVarType() == TokenType.SONG) {
            objCalle = new Song();
        } else if (calee.getVarType() == TokenType.EFFECT) {
            objCalle = new Effect();
        } else if (calee.getVarType() == TokenType.SONG) {
            objCalle = new Song();
        } else if (calee.getVarType() == TokenType.EFFECTS) {
            objCalle = new Effects();
        } else {
            throw new Exception("Not an object type: " + calee.getVarType());
        }

        if(!objCalle.hasMethod(operand.getName())) {
            throw new Exception(calee.getVarType().toString() + " does not have method: " + operand.getName());
        }

        if(objCalle.getRetType(operand.getName()) != type) {
            boolean isIntAddedToString = type == TokenType.STRING && objCalle.getRetType(operand.getName()) == TokenType.INT
                    && operation == TokenType.PLUS;
            if (isIntAddedToString) {
                expressionEx.addOperand(checkMethodCall(scope, operand, TokenType.INT));
                return;
            } else {
                if(type == TokenType.STRING && objCalle.getRetType(operand.getName()) == TokenType.INT && operation != null) {
                    throw new Exception("Incompatible operation! Cannot use [" + operation + "] to concatenate int with string");
                } else {
                    throw new Exception("Incompatible types! Requierd [" + type + "]");
                }
            }
        }
        expressionEx.addOperand(checkMethodCall(scope, operand, type));
    }

    private IntLiteralEx checkIntLiteral(Scope scope, IntLiteral operand) throws Exception {
        final IntLiteralEx intLiteralEx = new IntLiteralEx();
        intLiteralEx.setValue(operand.getValue());
        return intLiteralEx;
    }

    private StringLiteralEx checkStringLiteral(Scope scope, IntLiteral operand) throws Exception {
        final StringLiteralEx stringLiteralEx = new StringLiteralEx();
        stringLiteralEx.setValue(Integer.toString(operand.getValue()));
        return stringLiteralEx;
    }

    private StringLiteralEx checkStringLiteral(Scope scope, StringLiteral operand) throws Exception {
        final StringLiteralEx stringLiteralEx = new StringLiteralEx();
        stringLiteralEx.setValue(operand.getValue());
        return stringLiteralEx;
    }

    private VariableEx checkVariable(Scope scope, Variable operand, TokenType type) throws Exception {
        final VariableEx variableEx = new VariableEx();
        if(!(scope.hasVariable(operand.getName()))) {
            throw new Exception("Usage of undefined variable: " + operand.getName());
        }

        variableEx.setName(operand.getName());
        return variableEx;
    }

    private Call checkFunCall(final Scope scope, final FunCall operand, final TokenType type) throws Exception {
        if(!definedFunctions.containsKey(operand.getName())) {
            throw new Exception("Undefined function: " + operand.getName());
        }
        final Function function = definedFunctions.get(operand.getName()).getFunction();

        if (type != null && type != function.getRetType()) {
            throw new Exception("Incompatible types! Requierd [" + type + "]");
        } else if(definedFunctions.get(operand.getName()).getFunction().getParameters().size() != operand.getArguments().size()) {
            throw new Exception("Invalid number of arguments " + operand.getArguments().size() + " != " +
                    definedFunctions.get(operand.getName()).getFunction().getParameters().size());
        }

        final Call call = new Call();
        call.setName(operand.getName());
        for (int i = 0; i < operand.getArguments().size(); ++i) {
            final Node argument = operand.getArguments().get(i);
            final Variable variable = (Variable) definedFunctions.get(operand.getName()).getFunction().getParameters().get(i);
            final TokenType argumentType = variable.getVarType();
            call.addArgument(checkAssignable(scope, argument, argumentType));
        }

        return call;

    }

    private MethodCallEx checkMethodCall(Scope scope, MethodCall methodCall, TokenType type) throws Exception {
        if(!scope.hasVariable(methodCall.getCalee())) {
            throw new Exception("Undefined object: " + methodCall.getCalee());
        }

        execute.Object objCalle;
        Variable calee = scope.getVariableByName(methodCall.getCalee());
        if(calee.getVarType() == TokenType.SONG) {
            objCalle = new Song();
        } else if (calee.getVarType() == TokenType.EFFECT) {
            objCalle = new Effect();
        } else if (calee.getVarType() == TokenType.SONG) {
            objCalle = new Song();
        } else if (calee.getVarType() == TokenType.EFFECTS) {
            objCalle = new Effects();
        } else {
            throw new Exception("Not an object type: " + calee.getVarType());
        }
        objCalle.setName(calee.getName());

        if(type != null && objCalle.getRetType(methodCall.getName()) != type) {
            throw new Exception("Incompatible types! Requierd [" + type + "]");
        }

        if(methodCall.getArguments().size() != objCalle.getArgumentsType(methodCall.getName()).size()) {
            throw new Exception("Invalid number of arguments " + methodCall.getArguments().size() + " != " +
                    objCalle.getArgumentsType(methodCall.getName()).size());
        }

        final MethodCallEx methodCallEx = new MethodCallEx();
        methodCallEx.setCalee(objCalle);
        methodCallEx.setCaleeType(objCalle.getObjType());
        methodCallEx.setName(methodCall.getName());

        for(int i = 0; i < methodCall.getArguments().size(); ++i) {
            final Node argument = methodCall.getArguments().get(i);
            final TokenType argumentType = objCalle.getArgumentsType(methodCall.getName()).get(i);
            methodCallEx.addArgument(checkAssignable(scope, argument, argumentType));
        }

        scope.setVariable(calee.getName(), new Value(objCalle));

        return methodCallEx;
    }

    private Instruction checkReturnStatement(final Scope scope, final ReturnStatement returnStatement, final TokenType returnedType) throws Exception {
        final Return ret = new Return();
        ret.setValue(checkAssignable(scope, returnStatement.getReturnValue(), returnedType));
        return ret;
    }

    private Instruction checkIfStatement(final Scope scope, final IfStatement ifStatement, final TokenType type) throws Exception {
        final IfEx ifEx = new IfEx();
        ifEx.setCondition(checkCondition(scope, ifStatement.getCondition()));
        ifEx.setTrueBlock(checkBlock(scope, ifStatement.getTrueBlock(), type));
        if(ifStatement.hasElseBlock()) {
            ifEx.setElseBlock(checkBlock(scope, ifStatement.getElseBlock(), type));
        }

        return ifEx;
    }

    private ConditionEx checkCondition(final Scope scope, final Condition condition) throws Exception {
        final ConditionEx conditionEx = new ConditionEx();
        conditionEx.setIsNegated(condition.isNegated());
        conditionEx.setOperation(condition.getOperator());
        for (final Node operand : condition.getOperands()) {
            switch (operand.getType()) {
                case Condition:
                    conditionEx.addOpernad(checkCondition(scope, (Condition) operand));
                    break;
                case Variable:
                    conditionEx.addOpernad(checkVariable(scope, (Variable) operand, null));
                    break;
                case IntLiteral:
                    conditionEx.addOpernad(checkIntLiteral(scope, (IntLiteral) operand));
                    break;
                default:
                    break;
            }
        }
        return conditionEx;
    }

    private ForEx checkForStatement(final Scope scope, final ForStatement forStatement, TokenType type) throws Exception {
        ForEx forEx = new ForEx();
        forEx.setFrom(checkAssignment(scope, forStatement.getFrom(), TokenType.INT));
        forEx.setTo(checkAssignable(scope, forStatement.getTo(), TokenType.INT));
        forEx.setStep(forStatement.getStep());
        forEx.setBlock(checkBlock(scope, forStatement.getStatementBlock(), type));
        return forEx;
    }

    private void checkDeclaration(final Scope scope, final Declaration declaration) throws Exception {
        Variable variable = new Variable();
        variable.setVarType(declaration.getTokenType());
        variable.setName(declaration.getName());
        if(!scope.addVariable(variable)) {
            throw new Exception("Multiply declaration of: " + declaration.getName());
        }
    }

    private PRINT checkPrint(final Scope scope, final PrintStatement printStatement) throws Exception {
        PRINT print = new PRINT();
        Expression expression = new Expression();
        StringLiteral stringLiteral = new StringLiteral();
        stringLiteral.setValue("");
        expression.addOperand(stringLiteral);
        expression.addOperation(TokenType.PLUS);
        expression.addOperand(printStatement.getText());
        print.setText(checkAssignable(scope, expression, TokenType.STRING));
        return print;
    }

    private boolean isCorrectTypesAndOperation(TokenType requiredType, TokenType operandType, TokenType operation) throws Exception {
        if(requiredType == operandType) {
            if (requiredType == TokenType.STRING && operation != null && operation != TokenType.PLUS) {
                throw new Exception("Incompatible operation! Cannot use [" + operation + "]  with " + requiredType);
            } else if (requiredType != TokenType.INT && operation != null) {
                throw new Exception("Incompatible operation! Cannot use [" + operation + "]  with " + requiredType);
            } else {
                return true;
            }
        } else if (requiredType == TokenType.STRING && operation == TokenType.PLUS && operandType == TokenType.INT) {
            return true;
        } else {
            if( requiredType == TokenType.STRING && operandType == TokenType.INT && operation != null) {
                throw new Exception("Incompatible operation! Cannot use [" + operation + "] to concatenate int with string");
            } else {
                throw new Exception("Incompatible types! Requierd [" + requiredType + "]");
            }
        }
    }


}
