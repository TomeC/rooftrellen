package com.wkr.tp.ast.statement;

import com.wkr.tp.ast.Identifier;
import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.ast.base.AbstractStatement;
import com.wkr.tp.token.Token;

/**
 * @author wkr
 * @Description: let a=1
 * @date 2023/11/29 19:30
 */
public class LetStatement extends AbstractStatement {
    private Token token;
    private Identifier name;
    private AbstractExpression value;

    public LetStatement(Token token, Identifier name, AbstractExpression value) {
        this.token = token;
        this.name = name;
        this.value = value;
    }

    public LetStatement(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LetStatement: { " +
                token.getLiteral() +
                " " + name.toString() +
                " = " + value.toString() +
                " }";
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public AbstractExpression getValue() {
        return value;
    }

    public void setValue(AbstractExpression value) {
        this.value = value;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
