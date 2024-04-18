package com.wkr.tp.ast.expression;

import com.wkr.tp.ast.BlockStatement;
import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.token.Token;

/**
 * @author wangkun1-jk
 * @Description: if (expression) {blockStatement} else {blockStatement}
 * @date 2024/4/10 11:31
 */
public class IfExpression extends AbstractExpression {
    private Token token;
    private AbstractExpression condition;
    private BlockStatement consequenceStatement;
    private BlockStatement alternativeStatement;

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
        sb.append(token.toString())
                .append(condition.toString())
                .append(consequenceStatement.toString());
        if (alternativeStatement != null) {
            sb.append("else")
                    .append(alternativeStatement.toString());
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

    public BlockStatement getConsequenceStatement() {
        return consequenceStatement;
    }

    public void setConsequenceStatement(BlockStatement consequenceStatement) {
        this.consequenceStatement = consequenceStatement;
    }

    public BlockStatement getAlternativeStatement() {
        return alternativeStatement;
    }

    public void setAlternativeStatement(BlockStatement alternativeStatement) {
        this.alternativeStatement = alternativeStatement;
    }
}
