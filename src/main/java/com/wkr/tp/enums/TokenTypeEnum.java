package com.wkr.tp.enums;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wkr
 * @Description: token type enums
 * @date 2023/11/28 14:33
 */
public enum TokenTypeEnum {
    /**
     * token type enum
     */
    ILLEGAL(0, "ILLEGAL"),
    EOF(1, "EOF"),

    // add foobar x y and so on
    IDENT(2, "IDENT"),
    // 123456
    INT(3, "INT"),
    // "abc"
    STRING(4, "STRING"),

    // operators
    // =
    ASSIGN(5, "ASSIGN"),
    // +
    PLUS(6, "PLUS"),
    // -
    MINUS(7, "MINUS"),
    // !
    BANG(8, "BANG"),
    // *
    ASTERISK(9, "ASTERISK"),
    // /
    SLASH(10, "SLASH"),

    // <
    LT(11, "LT"),
    // >
    GT(12, "GT"),

    // ==
    EQ(13, "EQ"),
    //!=
    NOT_EQ(14, "NOT_EQ"),

    // delimiters
    // ,
    COMMA(15, "COMMA"),
    // ;
    SEMICOLON(16, "SEMICOLON"),
    // :
    COLON(17, "COLON"),

    // (
    LPAREN(18, "LPAREN"),
    // )
    RPAREN(19, "RPAREN"),
    // {
    LBRACE(20, "LBRACE"),
    // }
    RBRACE(21, "RBRACE"),
    // [
    LBRACKET(22, "LBRACKET"),
    // ]
    RBRACKET(23, "RBRACKET"),

    // keywords
    // FUNCTION
    FUNCTION(24, "FUNCTION"),
    // LET
    LET(25, "LET"),
    // TRUE
    TRUE(26, "TRUE"),
    // FALSE
    FALSE(27, "FALSE"),
    // IF
    IF(28, "IF"),
    // ELSE
    ELSE(29, "ELSE"),
    // RETURN
    RETURN(30, "RETURN"),
    // break
    BREAK(31, "BREAK"),
    // CONTINUE
    CONTINUE(32, "CONTINUE"),
    ;


    TokenTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;
    private final String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
    public boolean equalsCode(TokenTypeEnum tokenTypeEnum) {
        return tokenTypeEnum.getCode().equals(getCode());
    }
    public static TokenTypeEnum gettypeEnum(Integer type) {
        return Arrays.stream(TokenTypeEnum.values()).filter(item ->
                item.getCode().equals(type)
        ).findFirst().orElseThrow(() -> new InvalidParameterException("type not found"));
    }

    @Override
    public String toString() {
        return "TokenTypeEnum{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }


    public static TokenTypeEnum lookupIdent(String ident) {
        Map<String, TokenTypeEnum> keywords = new HashMap<>();
        keywords.put("fn", TokenTypeEnum.FUNCTION);
        keywords.put("let", TokenTypeEnum.LET);
        keywords.put("true", TokenTypeEnum.TRUE);
        keywords.put("false", TokenTypeEnum.FALSE);
        keywords.put("if", TokenTypeEnum.IF);
        keywords.put("else", TokenTypeEnum.ELSE);
        keywords.put("return", TokenTypeEnum.RETURN);

        return keywords.getOrDefault(ident, TokenTypeEnum.IDENT);
    }
}
