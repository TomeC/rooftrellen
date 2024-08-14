package com.wkr.tp;

import com.wkr.tp.ast.program.Program;
import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.ast.base.AbstractStatement;
import com.wkr.tp.ast.base.INode;
import com.wkr.tp.ast.expression.*;
import com.wkr.tp.ast.statement.BlockStatement;
import com.wkr.tp.ast.statement.ExpressionStatement;
import com.wkr.tp.ast.statement.LetStatement;
import com.wkr.tp.ast.statement.ReturnStatement;
import com.wkr.tp.enums.ObjectTypeEnum;
import com.wkr.tp.object.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wkr
 * @description
 * @date 2024/6/10
 */
public class Evaluator {
    public RtObject eval(INode node, Environment env) {
        if (node instanceof Program) {
            return evalProgram((Program)node, env);
        }
        if (node instanceof BlockStatement) {
            return evalBlockSstatement((BlockStatement)node, env);
        }
        if (node instanceof ExpressionStatement) {
            return eval(((ExpressionStatement) node).getExpression(), env);
        }
        if (node instanceof ReturnStatement) {
            RtObject val = eval(((ReturnStatement) node).getReturnExpression(), env);
            if (isError(val)) {
                return val;
            }
            return new ReturnValue(val);
        }
        if (node instanceof LetStatement) {
            RtObject val = eval(((LetStatement) node).getValue(), env);
            if (isError(val)) {
                return val;
            }
            env.set(((LetStatement)node).getName().getValue(), val);
        }
        if (node instanceof IntegerLiteral) {
            return new RtInteger(((IntegerLiteral)node).getValue());
        }
        if (node instanceof BooleanExpression) {
            return nativeBool2BooleanObject(((BooleanExpression)node).isValue());
        }
        if (node instanceof StringLiteral) {
            return new RtString(((StringLiteral)node).getValue());
        }
        if (node instanceof PrefixExpression) {
            RtObject right = eval(((PrefixExpression) node).getRight(), env);
            if (isError(right)) {
                return right;
            }
            return evalPrefixExpression(((PrefixExpression) node).getOperator(), right);
        }
        if (node instanceof InfixExpression) {
            InfixExpression expression = (InfixExpression) node;
            RtObject left = eval(expression.getLeftExp(), env);
            if (isError(left)) {
                return left;
            }
            RtObject right = eval(expression.getRightExp(), env);
            if (isError(right)) {
                return right;
            }
            return evalInfixExpression(expression.getOperator(), left, right);
        }
        if (node instanceof IfExpression) {
            return evalIfExpression((IfExpression)node, env);
        }
        if (node instanceof Identifier) {
            return evalIdentifier((Identifier)node, env);
        }
        if (node instanceof FunctionLiteral) {
            return new RtFunction(((FunctionLiteral) node).getParameterList(), ((FunctionLiteral) node).getBody(), env);
        }
        if (node instanceof CallExpression) {
            RtObject fun = eval(((CallExpression) node).getFunction(), env);
            List<RtObject> args = evalExpressions(((CallExpression) node).getArgumentList(), env);
            if (args.size() == 1 && isError(args.get(0))) {
                return args.get(0);
            }
            return applyFunction(fun, args);
        }
        if (node instanceof ArrayLiteral) {
            List<RtObject> retList = evalExpressions(((ArrayLiteral) node).getElementList(), env);
            if (retList.size() == 1 && isError(retList.get(0))) {
                return retList.get(0);
            }
            return new RtArray(retList);
        }
        if (node instanceof IndexExpression) {
            RtObject left = eval(((IndexExpression) node).getLeftExp(), env);
            if (isError(left)) {
                return left;
            }
            RtObject index = eval(((IndexExpression) node).getIndex(), env);
            if (isError(index)) {
                return index;
            }
            return evalIndexExpression(index, left);
        }
        return null;
    }

    private RtObject evalIndexExpression(RtObject index, RtObject left) {
        if (left.getType().equals(ObjectTypeEnum.ARRAY_OBJ)
                && index.getType().equals(ObjectTypeEnum.INTEGER_OBJ)) {
            return evalArrayIndexExpression(left, index);
        }
        // todo hash
        return new RtError("index operator not support:"+RtObject.objectTypeValue(left.getType()));
    }

    private RtObject evalArrayIndexExpression(RtObject left, RtObject index) {
        RtArray array = (RtArray) left;
        long i = ((RtInteger) index).getValue();
        if (i < 0 || i >= array.getElements().size()) {
            return new RtNull();
        }
        return array.getElements().get((int) i);
    }

    private RtObject applyFunction(RtObject fun, List<RtObject> args) {
        if (fun instanceof RtFunction) {
            Environment env = extendFunctionEnv((RtFunction) fun, args);
            RtObject val = eval(((RtFunction) fun).getBody(), env);
            return unwarpReturnValue(val);
        }
        // todo buildin
        return new RtError("not a function:"+RtObject.objectTypeValue(fun.getType()));
    }

    private RtObject unwarpReturnValue(RtObject val) {
        if (val instanceof ReturnValue) {
            return ((ReturnValue) val).getValue();
        }
        return val;
    }

    private Environment extendFunctionEnv(RtFunction fun, List<RtObject> args) {
        Environment extEnv = Environment.newEnclosedEnv(fun.getEnv());
        for (int i = 0; i < fun.getParams().size(); i++) {
            extEnv.set(fun.getParams().get(i).getValue(), args.get(i));
        }
        return extEnv;
    }

    private List<RtObject> evalExpressions(List<AbstractExpression> argumentList, Environment env) {
        List<RtObject> results = new ArrayList<>();
        for (AbstractExpression expression : argumentList) {
            RtObject val = eval(expression, env);
            if (isError(val)) {
                return Arrays.asList(val);
            }
            results.add(val);
        }
        return results;
    }

    private RtObject evalIdentifier(Identifier node, Environment env) {
        RtObject identifier = env.get(node.getValue());
        if (identifier != null) {
            return identifier;
        }
        // todo builtin
        return new RtError("identifier not found: "+node.getValue());
    }

    private RtObject evalIfExpression(IfExpression node, Environment env) {
        RtObject condition = eval(node.getCondition(), env);
        if (isError(condition)) {
            return condition;
        }
        if (isTruthy(condition)) {
            return eval(node.getConsequenceStatement(), env);
        } else if (node.getAlternativeStatement() != null) {
            return eval(node.getAlternativeStatement(), env);
        } else {
            return new RtNull();
        }
    }

    private boolean isTruthy(RtObject condition) {
        if (condition instanceof RtBoolean) {
            return ((RtBoolean) condition).isValue();
        }
        if (condition instanceof RtNull) {
            return false;
        }
        return true;
    }

    private RtObject evalInfixExpression(String op, RtObject left, RtObject right) {
        if (left.getType().equals(ObjectTypeEnum.INTEGER_OBJ) && right.getType().equals(ObjectTypeEnum.INTEGER_OBJ)) {
            return evalIntegerInfixExpression(op, left, right);
        }
        if (left.getType().equals(ObjectTypeEnum.STRING_OBJ) && right.getType().equals(ObjectTypeEnum.STRING_OBJ)) {
            return evalStringInfixExpression(op, left, right);
        }
        if (op.equals("==")) {
            if (left instanceof RtBoolean && right instanceof  RtBoolean) {
                return nativeBool2BooleanObject(((RtBoolean) left).isValue() == ((RtBoolean) right).isValue());
            }
        }
        if (op.equals("!=")) {
            if (left instanceof RtBoolean && right instanceof  RtBoolean) {
                // todo
                return nativeBool2BooleanObject(((RtBoolean) left).isValue() != ((RtBoolean) right).isValue());
            }
        }
        if (left.getType() != right.getType()) {
            return new RtError("type mismatch: "+RtObject.objectTypeValue(left.getType())+" "+op+" "
                            +RtObject.objectTypeValue(right.getType()));
        }
        return new RtError("unknown operator: "+RtObject.objectTypeValue(left.getType())+" "+op+" "
                +RtObject.objectTypeValue(right.getType()));

    }

    private RtObject evalStringInfixExpression(String op, RtObject left, RtObject right) {
        if (!op.equals("+")) {
            String errMsg = "unknown operator: "+RtObject.objectTypeValue(left.getType())+" "+op
                    + " "+RtObject.objectTypeValue(right.getType());
            return new RtError(errMsg);
        }
        String lv = ((RtString)left).getValue();
        String rv = ((RtString)right).getValue();
        return new RtString(lv + rv);
    }

    private RtObject evalIntegerInfixExpression(String op, RtObject left, RtObject right) {
        long lv = ((RtInteger)left).getValue();
        long rv = ((RtInteger)right).getValue();
        switch (op) {
            case "+":
                return new RtInteger(lv + rv);
            case "-":
                return new RtInteger(lv - rv);
            case "*":
                return new RtInteger(lv * rv);
            case "/":
                return new RtInteger(lv / rv);
            case "<":
                return nativeBool2BooleanObject(lv < rv);
            case ">":
                return nativeBool2BooleanObject(lv > rv);
            case "==":
                return nativeBool2BooleanObject(lv == rv);
            case "!=":
                return nativeBool2BooleanObject(lv != rv);
        }
        String errMsg = "unknown operator: "+RtObject.objectTypeValue(left.getType())+" "
                +op+" "+RtObject.objectTypeValue(right.getType());
        return new RtError(errMsg);
    }

    private RtObject evalPrefixExpression(String operator, RtObject right) {
        if ("!".equals(operator)) {
            return evalBangOperatorExpression(right);
        }
        if ("-".equals(operator)) {
            return evalMinusPrefixOperatorExpression(right);
        }
        return new RtError("unknown operator: "+operator+RtObject.objectTypeValue(right.getType()));
    }

    private RtObject evalMinusPrefixOperatorExpression(RtObject right) {
        if (!right.getType().equals(ObjectTypeEnum.INTEGER_OBJ)) {
            return new RtError("unknown operator: -"+RtObject.objectTypeValue(right.getType()));
        }
        return new RtInteger(-((RtInteger)right).getValue());
    }

    private RtObject evalBangOperatorExpression(RtObject right) {
        if (right instanceof RtBoolean) {
            return new RtBoolean(!((RtBoolean) right).isValue());
        }
        if (right instanceof RtNull) {
            return new RtBoolean(true);
        }
        return new RtBoolean(false);
    }

    private RtObject nativeBool2BooleanObject(boolean value) {
        return new RtBoolean(value);
    }

    private boolean isError(RtObject val) {
        if (val != null && val.getType().equals(ObjectTypeEnum.ERROR_OBJ)) {
            return true;
        }
        return false;
    }

    private RtObject evalBlockSstatement(BlockStatement node, Environment env) {
        RtObject result = null;
        for (AbstractStatement statement : node.getStatementList()) {
            result = eval(statement, env);
            if (result != null && (result.getType().equals(ObjectTypeEnum.RETURN_VALUE_OBJ)
                                    || result.getType().equals(ObjectTypeEnum.ERROR_OBJ))) {
                return result;
            }
        }
        return result;
    }

    private RtObject evalProgram(Program node, Environment env) {
        RtObject result = null;
        for (AbstractStatement statement : node.getStatementList()) {
            result = eval(statement, env);
            if (result instanceof ReturnValue) {
                return ((ReturnValue) result).getValue();
            }
            if (result instanceof RtError) {
                return result;
            }
        }
        return result;
    }
}
