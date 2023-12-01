package com.wkr.tp.ast;

import com.wkr.tp.ast.base.Expression;
import com.wkr.tp.ast.base.Statement;
import com.wkr.tp.token.Token;

/**
 * @author wkr
 * @Description: let statement
 * @date 2023/11/29 19:30
 */
public class LetStatement extends Statement {
    private Token token;
    private Identifier name;
    private Expression value;

    public LetStatement(Token token, Identifier name, Expression value) {
        this.token = token;
        this.name = name;
        this.value = value;
    }

    public LetStatement(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LetStatement{" +
                "token=" + token.getLiteral() +
                ", name=" + name.toString() +
                ", value=" + value.toString() +
                '}';
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }
}
