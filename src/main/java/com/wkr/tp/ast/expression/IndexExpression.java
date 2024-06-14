package com.wkr.tp.ast.expression;

import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.token.Token;

/**
 * @author 王锟
 * @description
 * @date 2024/6/13
 */
public class IndexExpression extends AbstractExpression {
    private Token token;
    private AbstractExpression leftExp;
    private AbstractExpression index;

    public IndexExpression(Token token) {
        this.token = token;
    }

    public IndexExpression(Token token, AbstractExpression leftExp) {
        this.token = token;
        this.leftExp = leftExp;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return "("+leftExp.toString()+"["+index.toString()+"])";
    }

    public Token getToken() {
        return token;
    }

    public AbstractExpression getLeftExp() {
        return leftExp;
    }

    public AbstractExpression getIndex() {
        return index;
    }

    public void setLeftExp(AbstractExpression leftExp) {
        this.leftExp = leftExp;
    }

    public void setIndex(AbstractExpression index) {
        this.index = index;
    }
}
