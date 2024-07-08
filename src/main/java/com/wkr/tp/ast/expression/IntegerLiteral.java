package com.wkr.tp.ast.expression;

import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.token.Token;

/**
 * @author wkr
 * @description
 * @date 2024/6/9
 */
public class IntegerLiteral extends AbstractExpression {
    private Token token;
    private long value;

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public IntegerLiteral(Token token, long value) {
        this.token = token;
        this.value = value;
    }

    public IntegerLiteral(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
