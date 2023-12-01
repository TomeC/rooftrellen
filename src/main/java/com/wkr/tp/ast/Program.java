package com.wkr.tp.ast;

import com.wkr.tp.ast.base.Node;
import com.wkr.tp.ast.base.Statement;

import java.util.ArrayList;

/**
 * @author wkr
 * @Description:
 * @date 2023/11/27 19:51
 */
public class Program implements Node {
    private ArrayList<Statement> statementList;

    @Override
    public String tokenLiteral() {
        if (!statementList.isEmpty()) {
            return statementList.get(0).tokenLiteral();
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Program{\n");
        builder.append("statementList=");
        for (Statement stm : statementList) {
            builder.append(stm.toString());
        }
        return builder.append("}\n").toString();
    }

    public Program() {
        statementList = new ArrayList<>();
    }

    public ArrayList<Statement> getStatementList() {
        return statementList;
    }

    public void setStatementList(ArrayList<Statement> statementList) {
        this.statementList = statementList;
    }
    public void addStatement(Statement statement) {
        statementList.add(statement);
    }
}
