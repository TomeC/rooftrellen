package com.wkr.tp.object;

import com.wkr.tp.enums.ObjectTypeEnum;

import java.util.List;

/**
 * @author wangkun1-jk
 * @Description:
 * @date 2024/7/8 18:02
 */
public class RtArray extends RtObject {
    private List<RtObject> elements;

    public RtArray(List<RtObject> elements) {
        this.elements = elements;
    }

    @Override
    public ObjectTypeEnum getType() {
        return ObjectTypeEnum.ARRAY_OBJ;
    }

    @Override
    public String inspect() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (RtObject el : elements) {
            sb.append(el.inspect());
            sb.append(',');
        }
        sb.setCharAt(sb.length()-1, ']');
        return sb.toString();
    }

    public List<RtObject> getElements() {
        return elements;
    }
}
