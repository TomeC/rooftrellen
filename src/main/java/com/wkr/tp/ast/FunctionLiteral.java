package com.wkr.tp.ast;

import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.token.Token;

import java.util.List;

/**
 * @author wangkun1-jk
 * @Description: let ef = fn(x) {let a=1;}
 * @date 2024/4/8 19:10
 */
public class FunctionLiteral extends AbstractExpression  {
    private Token token;
    private List<Identifier> parameterList;
    private BlockStatement body;

    public FunctionLiteral(Token token) {
        this.token = token;
    }

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTokenLiteral());
        sb.append('(');
        for (Identifier identifier : parameterList) {
            sb.append(identifier.toString());
            sb.append(',');
        }
        sb.setCharAt(sb.length()-1, ')');
        sb.append(body.toString());
        return super.toString();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public List<Identifier> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<Identifier> parameterList) {
        this.parameterList = parameterList;
    }

    public BlockStatement getBody() {
        return body;
    }

    public void setBody(BlockStatement body) {
        this.body = body;
    }
}
