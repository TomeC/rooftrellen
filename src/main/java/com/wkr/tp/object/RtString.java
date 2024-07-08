package com.wkr.tp.object;

import com.wkr.tp.enums.ObjectTypeEnum;

/**
 * @author 王锟
 * @description
 * @date 2024/6/14
 */
public class RtString extends RtObject {
    private String value;

    public RtString(String value) {
        this.value = value;
    }

    @Override
    public ObjectTypeEnum getType() {
        return ObjectTypeEnum.STRING_OBJ;
    }

    @Override
    public String inspect() {
        return value;
    }

    public String getValue() {
        return value;
    }
}
