package com.wkr.tp.object;

import com.wkr.tp.enums.ObjectTypeEnum;

/**
 * @author wkr
 * @description
 * @date 2024/6/10
 */
public abstract class RtObject {
    public ObjectTypeEnum type() {
        return ObjectTypeEnum.NULL_OBJ;
    }
    public String inspect() {
        return "";
    }
    public String objectTypeValue(ObjectTypeEnum typeEnum) {
        return typeEnum.getValue();
    }
}
