package com.wkr.tp.ast;

import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.ast.base.AbstractStatement;
import com.wkr.tp.token.Token;

/**
 * @author wangkun1-jk
 * @Description:
 * {let a=1; let b=1;}
 * @date 2024/4/8 14:39
 */
public class ExpressionStatement extends AbstractStatement {
    private Token token;
    private AbstractExpression expression;

    public ExpressionStatement(Token token) {
        this.token = token;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return expression.toString();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public AbstractExpression getExpression() {
        return expression;
    }

    public void setExpression(AbstractExpression expression) {
        this.expression = expression;
    }
}
