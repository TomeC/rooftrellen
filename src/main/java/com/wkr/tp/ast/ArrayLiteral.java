package com.wkr.tp.ast;


import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.token.Token;

import java.util.List;

/**
 * @author wangkun1-jk
 * @Description: [1,2,3]
 * @date 2024/4/7 17:02
 */
public class ArrayLiteral extends AbstractExpression {
    private Token token;
    private List<AbstractExpression> elementList;

    public ArrayLiteral(Token token) {
        this.token = token;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (AbstractExpression expression : elementList) {
            sb.append(expression.toString());
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.setCharAt(sb.length()-1, ']');
        return sb.toString();
    }

    public Token getToken() {
        return token;
    }

    public List<AbstractExpression> getElementList() {
        return elementList;
    }

    public void setElementList(List<AbstractExpression> elementList) {
        this.elementList = elementList;
    }
}
