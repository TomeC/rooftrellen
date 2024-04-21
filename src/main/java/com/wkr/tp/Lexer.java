package com.wkr.tp;

import com.wkr.tp.token.Token;

import java.util.ArrayList;

/**
 * @author wangkun1-jk
 * @Description:
 * @date 2023/11/29 20:10
 */
public class Lexer {
    private char[] input;
    private int position;
    private int readPosition;
    private char currentChar;

    public Lexer(String s) {
        this.input = s.toCharArray();
        position = 0;
        readPosition = 0;
        currentChar = ' ';
    }
    public void readChar() {

    }
    public void peekChar() {

    }
    public void skipWhiteSpace() {
        while (currentChar == '' || currentChar == ' ' || currentChar == '\t'
                || currentChar == '\n' || currentChar == '\r') {
            readChar();
        }
    }
    public Token nextToken() {
        Token token;
        skipWhiteSpace();
        return token;
    }

}
