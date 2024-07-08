package com.wkr.tp.object;

import com.wkr.tp.enums.ObjectTypeEnum;

/**
 * @author 王锟
 * @description
 * @date 2024/6/18
 */
public class RtError extends RtObject {
    private String message;

    public RtError(String message) {
        this.message = message;
    }

    @Override
    public ObjectTypeEnum getType() {
        return ObjectTypeEnum.ERROR_OBJ;
    }

    @Override
    public String inspect() {
        return "Error: "+message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
