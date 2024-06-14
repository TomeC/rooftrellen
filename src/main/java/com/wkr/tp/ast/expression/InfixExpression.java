package com.wkr.tp.ast.expression;

import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.token.Token;

/**
 * @author wkr
 * @description
 * @date 2024/6/13
 */
public class InfixExpression extends AbstractExpression {
    private Token token;
    private AbstractExpression leftExp;
    private String operator;
    private AbstractExpression rightExp;

    public InfixExpression(Token token, AbstractExpression leftExp, String operator) {
        this.token = token;
        this.leftExp = leftExp;
        this.operator = operator;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return "("+leftExp.toString()+" "+operator+" "+rightExp.toString()+")";
    }

    public Token getToken() {
        return token;
    }

    public AbstractExpression getLeftExp() {
        return leftExp;
    }

    public String getOperator() {
        return operator;
    }

    public AbstractExpression getRightExp() {
        return rightExp;
    }

    public void setRightExp(AbstractExpression rightExp) {
        this.rightExp = rightExp;
    }
}
