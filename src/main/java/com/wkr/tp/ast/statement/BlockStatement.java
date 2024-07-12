package com.wkr.tp.ast.statement;

import com.wkr.tp.ast.base.AbstractStatement;
import com.wkr.tp.token.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangkun1-jk
 * @Description: { }
 * @date 2024/4/7 17:24
 */
public class BlockStatement extends AbstractStatement {
    private Token token;
    private List<AbstractStatement> statementList;

    @Override
    public String getTokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AbstractStatement statement : statementList) {
            sb.append(statement.toString());
        }
        return sb.toString();
    }

    public BlockStatement(Token token) {
        this.token = token;
        statementList = new ArrayList<>();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public List<AbstractStatement> getStatementList() {
        return statementList;
    }

    public void setStatementList(List<AbstractStatement> statementList) {
        this.statementList = statementList;
    }
}
