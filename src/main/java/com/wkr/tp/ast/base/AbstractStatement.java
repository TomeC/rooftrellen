package com.wkr.tp.ast.base;

/**
 * @author wkr
 * @Description: statement
 * @date 2023/11/27 19:46
 */
public abstract class AbstractStatement implements INode {
    @Override
    public String getTokenLiteral() {
        return "";
    }
}
