package com.wkr.tp.object;

import com.wkr.tp.enums.ObjectTypeEnum;

/**
 * @author 王锟
 * @description
 * @date 2024/6/14
 */
public class ReturnValue extends RtObject {
    private RtObject value;

    public ReturnValue(RtObject value) {
        this.value = value;
    }

    @Override
    public ObjectTypeEnum getType() {
        return ObjectTypeEnum.RETURN_VALUE_OBJ;
    }

    @Override
    public String inspect() {
        return value.inspect();
    }

    public RtObject getValue() {
        return value;
    }
}
