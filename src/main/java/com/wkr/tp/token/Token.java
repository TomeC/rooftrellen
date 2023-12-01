package com.wkr.tp.token;

import com.wkr.tp.enums.TokenTypeEnum;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangkun1-jk
 * @Description: token
 * @date 2023/11/28 19:45
 */
public class Token {
    private TokenTypeEnum tokenType;
    private  String literal;

    public static Map<String, TokenTypeEnum> keywordMap = new HashMap<>();
    static {
        keywordMap.put("fn", TokenTypeEnum.FUNCTION);
        keywordMap.put("let", TokenTypeEnum.LET);
        keywordMap.put("true", TokenTypeEnum.TRUE);
        keywordMap.put("false", TokenTypeEnum.FALSE);
        keywordMap.put("if", TokenTypeEnum.IF);
        keywordMap.put("else", TokenTypeEnum.ELSE);
        keywordMap.put("return", TokenTypeEnum.RETURN);
        keywordMap.put("break", TokenTypeEnum.BREAK);
        keywordMap.put("continue", TokenTypeEnum.CONTINUE);
    }

    public Token(TokenTypeEnum tokenType, String literal) {
        this.tokenType = tokenType;
        this.literal = literal;
    }

    public TokenTypeEnum getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenTypeEnum tokenType) {
        this.tokenType = tokenType;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", literal='" + literal + '\'' +
                '}';
    }
    TokenTypeEnum lookupIdent(String ident) {
        TokenTypeEnum tokenTypeEnum = keywordMap.get(ident);
        if (tokenTypeEnum != null) {
            return tokenTypeEnum;
        }
        return TokenTypeEnum.IDENT;
    }
    public static String tokenTypeValue(TokenTypeEnum tokenTypeEnum) {
        return keywordMap.entrySet().stream().filter(item -> item.getValue().equalsCode(tokenTypeEnum)).findFirst()
                .orElseThrow(() -> new InvalidParameterException(tokenTypeEnum.getDesc()+" not exist")).getKey();
    }
}
