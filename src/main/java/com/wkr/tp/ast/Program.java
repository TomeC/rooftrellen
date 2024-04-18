package com.wkr.tp.ast;

import com.wkr.tp.ast.base.INode;
import com.wkr.tp.ast.base.AbstractStatement;

import java.util.ArrayList;

/**
 * @author wkr
 * @Description: { }
 * @date 2023/11/27 19:51
 */
public class Program implements INode {
    private ArrayList<AbstractStatement> abstractStatementList;

    @Override
    public String getTokenLiteral() {
        if (!abstractStatementList.isEmpty()) {
            return abstractStatementList.get(0).getTokenLiteral();
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Program{\n");
        builder.append("statementList=");
        for (AbstractStatement stm : abstractStatementList) {
            builder.append(stm.toString());
        }
        return builder.append("}\n").toString();
    }

    public Program() {
        abstractStatementList = new ArrayList<>();
    }

    public ArrayList<AbstractStatement> getStatementList() {
        return abstractStatementList;
    }

    public void setStatementList(ArrayList<AbstractStatement> abstractStatementList) {
        this.abstractStatementList = abstractStatementList;
    }
    public void addStatement(AbstractStatement abstractStatement) {
        abstractStatementList.add(abstractStatement);
    }
}
