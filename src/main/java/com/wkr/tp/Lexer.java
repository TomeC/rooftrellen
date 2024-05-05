package com.wkr.tp;

import com.wkr.tp.enums.TokenTypeEnum;
import com.wkr.tp.token.Token;

/**
 * @author wangkun1-jk
 * @Description:
 * @date 2023/11/29 20:10
 */
public class Lexer {
    private char[] input;
    private int readPosition;
    private char currentChar;

    public Lexer(String s) {
        this.input = s.toCharArray();
        readPosition = 0;
        currentChar = ' ';
    }
    public void readChar() {
        if (readPosition >= input.length) {
            currentChar = 0;
        } else {
            currentChar = input[readPosition];
        }
        readPosition++;
    }
    public char peedChar() {
        if (readPosition >= input.length) {
            return 0;
        } else {
            return input[readPosition];
        }
    }
    String captureChar(int num) {
        StringBuilder sb = new StringBuilder();
        sb.append(currentChar);
        while (--num > 0) {
            readChar();
            sb.append(currentChar);
        }
        return sb.toString();
    }
    public void skipWhiteSpace() {
        while (currentChar == ' ' || currentChar == '\t'
                || currentChar == '\n' || currentChar == '\r') {
            readChar();
        }
    }
    public Token nextToken() {
        Token token;
        skipWhiteSpace();
        switch (currentChar) {
            case '=':
                if (peedChar() == '=') {
                    token = new Token(TokenTypeEnum.EQ, captureChar(2));
                } else {
                    token = new Token(TokenTypeEnum.ASSIGN, String.valueOf(currentChar));
                }
                break;
            case '+':
                token = new Token(TokenTypeEnum.PLUS, String.valueOf(currentChar));
                break;
            case '-':
                token = new Token(TokenTypeEnum.MINUS, String.valueOf(currentChar));
                break;
            case '!':
                if (peedChar() == '=') {
                    token = new Token(TokenTypeEnum.NOT_EQ, captureChar(2));
                } else {
                    token = new Token(TokenTypeEnum.BANG, String.valueOf(currentChar));
                }
                break;
            case '/':
                token = new Token(TokenTypeEnum.SLASH, String.valueOf(currentChar));
                break;
            case '*':
                token = new Token(TokenTypeEnum.ASTERISK, String.valueOf(currentChar));
                break;
            case '<':
                token = new Token(TokenTypeEnum.LT, String.valueOf(currentChar));
                break;
            case '>':
                token = new Token(TokenTypeEnum.GT, String.valueOf(currentChar));
                break;
            case ';':
                token = new Token(TokenTypeEnum.SEMICOLON, String.valueOf(currentChar));
                break;
            case ':':
                token = new Token(TokenTypeEnum.COLON, String.valueOf(currentChar));
                break;
            case ',':
                token = new Token(TokenTypeEnum.COMMA, String.valueOf(currentChar));
                break;
            case '{':
                token = new Token(TokenTypeEnum.LBRACE, String.valueOf(currentChar));
                break;
            case '}':
                token = new Token(TokenTypeEnum.RBRACE, String.valueOf(currentChar));
                break;
            case '(':
                token = new Token(TokenTypeEnum.LPAREN, String.valueOf(currentChar));
                break;
            case ')':
                token = new Token(TokenTypeEnum.RPAREN, String.valueOf(currentChar));
                break;
            case '[':
                token = new Token(TokenTypeEnum.LBRACKET, String.valueOf(currentChar));
                break;
            case ']':
                token = new Token(TokenTypeEnum.RBRACKET, String.valueOf(currentChar));
                break;
            case '"':
                token = new Token(TokenTypeEnum.STRING, readString());
                break;
            case '0':
                token = new Token(TokenTypeEnum.STRING, "");
                break;
            default:
                if (isLetter(currentChar)) {
                    String literal = readIdentifier();
                    return new Token(TokenTypeEnum.lookupIdent(literal), literal);
                } else if (isDigit(currentChar)) {
                    String literal = readNum();
                    return new Token(TokenTypeEnum.INT, literal);
                } else {
                    token = new Token(TokenTypeEnum.ILLEGAL, String.valueOf(currentChar));
                }
                break;
        }
        readChar();
        return token;
    }

    private String readIdentifier() {
        StringBuilder sb = new StringBuilder();
        while (isLetter(currentChar)) {
            sb.append(currentChar);
            readChar();
        }
        return sb.toString();
    }
    private String readNum() {
        StringBuilder sb = new StringBuilder();
        while (isDigit(currentChar)) {
            sb.append(currentChar);
            readChar();
        }
        return sb.toString();
    }

    public boolean isLetter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_';
    }
    public boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public String readString() {
        int pos = readPosition+1;
        for (int i = 0; i < 20; i++) {
            readChar();
            if (currentChar == '"' || currentChar == 0) {
                break;
            }
        }
        return new String(input, pos, readPosition-pos);
    }
}
