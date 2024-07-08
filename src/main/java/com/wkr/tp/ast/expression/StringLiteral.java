package com.wkr.tp.ast.expression;

import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.token.Token;

/**
 * @author wkr
 * @description "hello"
 * @date 2024/6/9
 */
public class StringLiteral extends AbstractExpression {
    private String value;
    private Token token;

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return token.getLiteral();
    }

    public StringLiteral(Token token, String value) {
        this.value = value;
        this.token = token;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
