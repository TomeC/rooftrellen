package com.wkr.tp.object;

import com.wkr.tp.enums.ObjectTypeEnum;

/**
 * @author 王锟
 * @description
 * @date 2024/6/18
 */
public class RtNull extends RtObject {
    @Override
    public ObjectTypeEnum getType() {
        return ObjectTypeEnum.NULL_OBJ;
    }

    @Override
    public String inspect() {
        return "null";
    }
}
