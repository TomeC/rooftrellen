package com.wkr.tp.ast.expression;

import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.token.Token;

/**
 * @author wkr
 * @description ! -
 * @date 2024/6/9
 */
public class PrefixExpression extends AbstractExpression {
    private Token token;
    private String operator;
    private AbstractExpression right;

    public PrefixExpression(Token token, String operator) {
        this.token = token;
        this.operator = operator;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return "("+operator+right.toString()+")";
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public AbstractExpression getRight() {
        return right;
    }

    public void setRight(AbstractExpression right) {
        this.right = right;
    }
}
