package com.wkr.tp.ast.base;



/**
 * @author wkr
 * @Description: expression
 * @date 2023/11/27 19:45
 */
public abstract class Expression implements Node {
    @Override
    public String tokenLiteral() {
        return "";
    }
}
