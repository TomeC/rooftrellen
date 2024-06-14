package com.wkr.tp.enums;

/**
 * @author wkr
 * @description
 * @date 2024/6/10
 */
public enum  ObjectTypeEnum {
    /**
     * "NULL"
     */
    NULL_OBJ("NULL"),
    /**
     *  "ERROR"
     */
    ERROR_OBJ("ERROR"),
    /**
     * "STRING"
     */
    STRING_OBJ("STRING"),
    /**
     * "INTEGER"
     */
    INTEGER_OBJ("INTEGER"),
    /**
     * "BOOLEAN"
     */
    BOOLEAN_OBJ("BOOLEAN"),
    /**
     * "RETURN_VALUE"
     */
    RETURN_VALUE_OBJ("RETURN_VALUE"),
    /**
     * "FUNCTION"
     */
    FUNCTION_OBJ("FUNCTION"),
    /**
     * "BUILTIN"
     */
    BUILTIN_OBJ("BUILTIN"),
    /**
     * "ARRAY"
     */
    ARRAY_OBJ("ARRAY"),
    /**
     * "HASH"
     */
    HASH_OBJ("HASH"),
    ;
    private String value;

    public String getValue() {
        return value;
    }

    ObjectTypeEnum(String value) {
        this.value = value;
    }
}
