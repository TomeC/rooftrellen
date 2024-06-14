package com.wkr.tp.enums;

/**
 * @author wkr
 * @description 优先级
 * @date 2024/6/9
 */
public enum PrecedenceEnum {
    /**
     * 优先级
     */
    LOWEST(0),
    // ==
    EQUALS(1),
    // > or <
    LESSGREATER(2),
    // +
    SUM(3),
    // *
    PRODUCT(4),
    // -X or !X
    PREFIX(5),
    // myFunction(X)
    CALL(6),
    // array[index]
    INDEX(7),
    ;
    private int code;

    PrecedenceEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public boolean gt(PrecedenceEnum precedence) {
        return code > precedence.getCode();
    }
    public boolean lt(PrecedenceEnum precedence) {
        return code < precedence.getCode();
    }
}
