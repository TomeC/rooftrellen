package com.wkr.tp.object;

import com.wkr.tp.enums.ObjectTypeEnum;

/**
 * @author 王锟
 * @description
 * @date 2024/6/14
 */
public class RtInteger extends RtObject {
    private long value;

    public RtInteger(long value) {
        this.value = value;
    }

    @Override
    public ObjectTypeEnum getType() {
        return ObjectTypeEnum.INTEGER_OBJ;
    }

    @Override
    public String inspect() {
        return String.valueOf(value);
    }

    public long getValue() {
        return value;
    }
}
