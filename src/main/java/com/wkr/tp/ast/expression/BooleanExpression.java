package com.wkr.tp.ast.expression;

import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.token.Token;

/**
 * @author wangkun1-jk
 * @Description: !exp or a>b
 * @date 2024/4/7 19:19
 */
public class BooleanExpression extends AbstractExpression {
    private Token token;
    private boolean value;

    public BooleanExpression(Token token, boolean b) {
        this.token = token;
        value =b;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return token.getLiteral();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
