package com.wkr.tp.ast.expression;

import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.token.Token;

/**
 * @author wangkun1-jk
 * @Description: a int
 * @date 2023/11/29 19:33
 */
public class Identifier extends AbstractExpression {
    private Token token;
    private String value;

    public Identifier(Token token, String value) {
        this.token = token;
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    public Token getToken() {
        return token;
    }

    public String getValue() {
        return value;
    }

}
