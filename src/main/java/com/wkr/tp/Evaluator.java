package com.wkr.tp;

import com.wkr.tp.ast.Program;
import com.wkr.tp.ast.base.INode;
import com.wkr.tp.ast.statement.BlockStatement;
import com.wkr.tp.ast.statement.ExpressionStatement;
import com.wkr.tp.ast.statement.ReturnStatement;
import com.wkr.tp.object.RtObject;

/**
 * @author wkr
 * @description
 * @date 2024/6/10
 */
public class Evaluator {
//    public RtObject eval(INode node, Environment env) {
//        if (node instanceof Program) {
//            return evalProgram((Program)node, env);
//        } else if (node instanceof BlockStatement) {
//            return evalBlockSstatement((BlockStatement)node, env);
//        } else if (node instanceof ExpressionStatement) {
//            return eval(((ExpressionStatement) node).getExpression(), env);
//        } else if (node instanceof ReturnStatement) {
//            RtObject val = eval(((ReturnStatement) node).getReturnExpression(), env);
//            if (isError(val)) {
//                return val;
//            }
//            env.set(node.getTokenLiteral(), val);
//        }
//    }
}
