package com.wkr.tp.object;

import com.wkr.tp.Environment;
import com.wkr.tp.ast.expression.Identifier;
import com.wkr.tp.ast.statement.BlockStatement;
import com.wkr.tp.enums.ObjectTypeEnum;

import java.util.List;

/**
 * @author wangkun1-jk
 * @Description:
 * @date 2024/6/28 21:06
 */
public class RtFunction extends RtObject {
    private List<Identifier> params;
    private BlockStatement body;
    private Environment env;

    public RtFunction(List<Identifier> params, BlockStatement body, Environment env) {
        this.params = params;
        this.body = body;
        this.env = env;
    }

    @Override
    public ObjectTypeEnum getType() {
        return ObjectTypeEnum.FUNCTION_OBJ;
    }

    @Override
    public String inspect() {
        StringBuilder sb = new StringBuilder();
        sb.append("fn(");
        for (Identifier identifier : params) {
            sb.append(identifier.toString());
            sb.append(",");
        }
        sb.setCharAt(sb.length()-1, ')');
        sb.append("{\n");
        sb.append(body.toString());
        sb.append("}\n");
        return sb.toString();
    }

    public List<Identifier> getParams() {
        return params;
    }

    public BlockStatement getBody() {
        return body;
    }

    public Environment getEnv() {
        return env;
    }
}
