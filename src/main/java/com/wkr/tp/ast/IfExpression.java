package com.wkr.tp.ast;

import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.token.Token;

public class IfExpression extends AbstractExpression {
    private Token token;
    private AbstractExpression condition;
    private BlockStatement consequence;
    private BlockStatement alternative;

    public IfExpression(Token token) {
        this.token = token;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("if")
                .append(condition.toString())
                .append(' ')
                .append(consequence.toString());
        if (alternative != null) {
            sb.append("else ")
                    .append(alternative.toString());
        }

        return sb.toString();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public AbstractExpression getCondition() {
        return condition;
    }

    public void setCondition(AbstractExpression condition) {
        this.condition = condition;
    }

    public BlockStatement getConsequence() {
        return consequence;
    }

    public void setConsequence(BlockStatement consequence) {
        this.consequence = consequence;
    }

    public BlockStatement getAlternative() {
        return alternative;
    }

    public void setAlternative(BlockStatement alternative) {
        this.alternative = alternative;
    }
}
