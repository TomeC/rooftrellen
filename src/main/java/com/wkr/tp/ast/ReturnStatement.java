package com.wkr.tp.ast;

import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.ast.base.AbstractStatement;
import com.wkr.tp.token.Token;

/**
 * @author wangkun1-jk
 * @Description: return a-1
 * @date 2024/4/7 17:37
 */
public class ReturnStatement extends AbstractStatement {
    private Token token;
    private AbstractExpression returnExpression;

    public ReturnStatement(Token token) {
        this.token = token;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return token.getLiteral()+" "+ returnExpression.toString()+";";
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public AbstractExpression getReturnExpression() {
        return returnExpression;
    }

    public void setReturnExpression(AbstractExpression returnExpression) {
        this.returnExpression = returnExpression;
    }
}
