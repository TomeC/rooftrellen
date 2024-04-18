package com.wkr.tp.ast;

import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.token.Token;

import java.util.Map;

/**
 * @author wangkun1-jk
 * @Description: {"hello":"world", "count":1}
 * @date 2024/4/10 11:17
 */
public class HashLiteral extends AbstractExpression {
    private Token token;
    private Map<AbstractExpression, AbstractExpression> pairs;

    public HashLiteral(Token token) {
        this.token = token;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        pairs.entrySet().forEach(item -> {
            sb.append(item.getKey().toString());
            sb.append(':');
            sb.append(item.getValue().toString());
            sb.append(',');
        });
        sb.setCharAt(sb.length()-1, '}');
        return sb.toString();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Map<AbstractExpression, AbstractExpression> getPairs() {
        return pairs;
    }

    public void setPairs(Map<AbstractExpression, AbstractExpression> pairs) {
        this.pairs = pairs;
    }
}
