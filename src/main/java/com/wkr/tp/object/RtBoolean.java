package com.wkr.tp.object;

import com.wkr.tp.enums.ObjectTypeEnum;

/**
 * @author 王锟
 * @description
 * @date 2024/6/18
 */
public class RtBoolean extends RtObject {
    private boolean value;

    public RtBoolean(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }

    @Override
    public ObjectTypeEnum getType() {
        return ObjectTypeEnum.BOOLEAN_OBJ;
    }

    @Override
    public String inspect() {
        return value?"true":"false";
    }
}
