package com.wkr.tp.parser;

import com.wkr.tp.ast.base.AbstractExpression;

/**
 * @author wkr
 * @description 处理函数
 * @date 2024/6/10
 */
@FunctionalInterface
public interface FunctionInFix {
    void inProcess(AbstractExpression expression);
}
