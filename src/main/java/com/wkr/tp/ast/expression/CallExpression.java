package com.wkr.tp.ast.expression;

import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.token.Token;

import java.util.List;

/**
 * @author wangkun1-jk
 * @Description: execute function(arg1, arg2)
 * @date 2024/4/7 19:26
 */
public class CallExpression extends AbstractExpression {
    private Token token;
    private AbstractExpression function;
    private List<AbstractExpression> argumentList;

    public CallExpression(Token token, AbstractExpression function) {
        this.token = token;
        this.function = function;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(function.toString());
        sb.append('(');
        for (AbstractExpression expression : argumentList) {
            sb.append(expression.toString())
                    .append(", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.setCharAt(sb.length()-1, ')');
        return sb.toString();
    }

    public Token getToken() {
        return token;
    }

    public AbstractExpression getFunction() {
        return function;
    }

    public List<AbstractExpression> getArgumentList() {
        return argumentList;
    }

    public void setArgumentList(List<AbstractExpression> argumentList) {
        this.argumentList = argumentList;
    }
}
