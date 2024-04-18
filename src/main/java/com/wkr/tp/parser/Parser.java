package com.wkr.tp.parser;

import com.wkr.tp.Lexer;
import com.wkr.tp.ast.LetStatement;
import com.wkr.tp.ast.Program;
import com.wkr.tp.ast.base.AbstractStatement;
import com.wkr.tp.enums.TokenTypeEnum;
import com.wkr.tp.token.Token;

import java.util.ArrayList;

/**
 * @author wkr
 * @Description: parser
 * @date 2023/11/27 19:49
 */
public class Parser {
    private Token currToken;
    private Token peekToken;
    private Lexer lexer;
    private ArrayList<String> errors;

    Program parseProgram() {
        Program program = new Program();
        while (!curTokenIs(TokenTypeEnum.EOF)) {
            AbstractStatement abstractStatement = parseStatement();
            if (abstractStatement != null) {
                program.addStatement(abstractStatement);
            }
        }
    }
    boolean curTokenIs(TokenTypeEnum tokenTypeEnum) {
        return tokenTypeEnum.equalsCode(currToken.getTokenType());
    }

    public AbstractStatement parseStatement() {
        switch (currToken.getTokenType()) {
            case LET:
                return parseLetStatement();
            default:

        }
        return null;
    }
    public LetStatement parseLetStatement() {
        AbstractStatement abstractStatement = new LetStatement(currToken);
        if (!expectPeek(TokenTypeEnum.IDENT)) {

        }
    }

    private boolean expectPeek(TokenTypeEnum tokenTypeEnum) {
        if (peekTokenIs(tokenTypeEnum)) {
            nextToken();
            return true;
        }
        peekError(tokenTypeEnum);
        return false;
    }

    private void peekError(TokenTypeEnum tokenTypeEnum) {
        String msg = "expect next token is "+ Token.tokenTypeValue(tokenTypeEnum) +
                ", but got " + Token.tokenTypeValue(peekToken.getTokenType());
        errors.add(msg);
    }

    private void nextToken() {
        currToken = peekToken;
        peekToken = lexer.nextToken();
    }

    private boolean peekTokenIs(TokenTypeEnum tokenTypeEnum) {
        return currToken.getTokenType() == tokenTypeEnum;
    }
}
