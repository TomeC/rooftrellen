package com.wkr.tp.parser;

import com.wkr.tp.Lexer;
import com.wkr.tp.ast.base.AbstractExpression;
import com.wkr.tp.ast.base.AbstractStatement;
import com.wkr.tp.ast.expression.*;
import com.wkr.tp.ast.program.Program;
import com.wkr.tp.ast.statement.*;
import com.wkr.tp.enums.PrecedenceEnum;
import com.wkr.tp.enums.TokenTypeEnum;
import com.wkr.tp.token.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wkr
 * @Description: parser
 * @date 2023/11/27 19:49
 */
public class Parser {
    private Logger logger = LogManager.getLogger(Parser.class);

    private Token currToken;
    private Token peekToken;
    private Lexer lexer;
    private List<String> errors = new ArrayList<>();
    private Map<TokenTypeEnum, Method> prefixFuns = new HashMap<>();
    private Map<TokenTypeEnum, Method> infixFuns = new HashMap<>();
    private Map<TokenTypeEnum, PrecedenceEnum> tokenPrecedenceMap = new HashMap<>();

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        init();
    }

    public Parser(String input) {
        this.lexer = new Lexer(input);
        init();
    }

    private void init() {
        tokenPrecedenceMap.put(TokenTypeEnum.EQ, PrecedenceEnum.EQUALS);
        tokenPrecedenceMap.put(TokenTypeEnum.NOT_EQ, PrecedenceEnum.EQUALS);
        tokenPrecedenceMap.put(TokenTypeEnum.LT, PrecedenceEnum.LESSGREATER);
        tokenPrecedenceMap.put(TokenTypeEnum.GT, PrecedenceEnum.LESSGREATER);
        tokenPrecedenceMap.put(TokenTypeEnum.PLUS, PrecedenceEnum.SUM);
        tokenPrecedenceMap.put(TokenTypeEnum.MINUS, PrecedenceEnum.SUM);
        tokenPrecedenceMap.put(TokenTypeEnum.SLASH, PrecedenceEnum.PRODUCT);
        tokenPrecedenceMap.put(TokenTypeEnum.ASTERISK, PrecedenceEnum.PRODUCT);
        tokenPrecedenceMap.put(TokenTypeEnum.LPAREN, PrecedenceEnum.CALL);
        tokenPrecedenceMap.put(TokenTypeEnum.LBRACKET, PrecedenceEnum.INDEX);

        try {
            // prefixFuns init
            prefixFuns.put(TokenTypeEnum.IDENT, this.getClass().getMethod("parseIdentifier"));
            prefixFuns.put(TokenTypeEnum.INT, this.getClass().getMethod("parseIntegerLiteral"));
            prefixFuns.put(TokenTypeEnum.STRING, this.getClass().getMethod("parseStringLiteral"));
            prefixFuns.put(TokenTypeEnum.BANG, this.getClass().getMethod("parsePrefixExpression"));
            prefixFuns.put(TokenTypeEnum.MINUS, this.getClass().getMethod("parsePrefixExpression"));
            prefixFuns.put(TokenTypeEnum.TRUE, this.getClass().getMethod("parseBoolean"));
            prefixFuns.put(TokenTypeEnum.FALSE, this.getClass().getMethod("parseBoolean"));
            prefixFuns.put(TokenTypeEnum.LPAREN, this.getClass().getMethod("parseGroupedExpression"));
            prefixFuns.put(TokenTypeEnum.IF, this.getClass().getMethod("parseIfExpression"));
            prefixFuns.put(TokenTypeEnum.FUNCTION, this.getClass().getMethod("parseFunctionLiteral"));
            prefixFuns.put(TokenTypeEnum.LBRACKET, this.getClass().getMethod("parseArrayLiteral"));
//            prefixFuns.put(TokenTypeEnum.LBRACE, this.getClass().getMethod("parseHashLiteral"));

            infixFuns.put(TokenTypeEnum.PLUS, this.getClass().getMethod("parseInfixExpression", AbstractExpression.class));
            infixFuns.put(TokenTypeEnum.MINUS, this.getClass().getMethod("parseInfixExpression", AbstractExpression.class));
            infixFuns.put(TokenTypeEnum.ASTERISK, this.getClass().getMethod("parseInfixExpression", AbstractExpression.class));
            infixFuns.put(TokenTypeEnum.SLASH, this.getClass().getMethod("parseInfixExpression", AbstractExpression.class));
            infixFuns.put(TokenTypeEnum.EQ, this.getClass().getMethod("parseInfixExpression", AbstractExpression.class));
            infixFuns.put(TokenTypeEnum.NOT_EQ, this.getClass().getMethod("parseInfixExpression", AbstractExpression.class));
            infixFuns.put(TokenTypeEnum.LT, this.getClass().getMethod("parseInfixExpression", AbstractExpression.class));
            infixFuns.put(TokenTypeEnum.GT, this.getClass().getMethod("parseInfixExpression", AbstractExpression.class));
            infixFuns.put(TokenTypeEnum.LPAREN, this.getClass().getMethod("parseCallExpression", AbstractExpression.class));
            infixFuns.put(TokenTypeEnum.LBRACKET, this.getClass().getMethod("parseIndexExpression", AbstractExpression.class));

        } catch (NoSuchMethodException e) {
            logger.error("初始化parser函数异常", e);
        }
        nextToken();
        nextToken();
    }

    public AbstractExpression parseArrayLiteral() {
        ArrayLiteral arrayLiteral = new ArrayLiteral(currToken);
        arrayLiteral.setElementList(parseExpressionList(TokenTypeEnum.RBRACKET));
        return arrayLiteral;
    }
    public AbstractExpression parseIndexExpression(AbstractExpression leftExp) {
        IndexExpression expression = new IndexExpression(currToken, leftExp);
        nextToken();
        expression.setIndex(parseExpression(PrecedenceEnum.LOWEST));
        if (!expectPeek(TokenTypeEnum.RBRACKET)) {
            return null;
        }
        return expression;
    }
    public AbstractExpression parseCallExpression(AbstractExpression leftExp) {
        CallExpression expression = new CallExpression(currToken, leftExp);
        expression.setArgumentList(parseExpressionList(TokenTypeEnum.RPAREN));
        return expression;
    }

    private List<AbstractExpression> parseExpressionList(TokenTypeEnum endTokenType) {
        List<AbstractExpression> parameters = new ArrayList<>();
        if (peekTokenIs(endTokenType)) {
            return parameters;
        }
        nextToken();
        parameters.add(parseExpression(PrecedenceEnum.LOWEST));
        while (peekTokenIs(TokenTypeEnum.COMMA)) {
            nextToken();
            nextToken();
            parameters.add(parseExpression(PrecedenceEnum.LOWEST));
        }
        if (!expectPeek(endTokenType)) {
            return null;
        }
        return parameters;
    }

    public AbstractExpression parseInfixExpression(AbstractExpression leftExp) {
        InfixExpression expression = new InfixExpression(currToken, leftExp, currToken.getLiteral());
        PrecedenceEnum precedence = curPrecedence();
        nextToken();
        AbstractExpression rightExp = parseExpression(precedence);
        expression.setRightExp(rightExp);
        return expression;

    }
    public AbstractExpression parseFunctionLiteral() {
        FunctionLiteral functionLiteral = new FunctionLiteral(currToken);
        if (!expectPeek(TokenTypeEnum.LPAREN)) {
            return null;
        }
        List<Identifier> identifierList = parseFunctionParameters();
        if (identifierList == null) {
            return null;
        }
        functionLiteral.setParameterList(identifierList);
        if (!expectPeek(TokenTypeEnum.LBRACE)) {
            return null;
        }
        functionLiteral.setBody(parseBlockStatement());
        return functionLiteral;
    }
    public List<Identifier> parseFunctionParameters() {
        List<Identifier> identifierList = new ArrayList<>();
        if (peekTokenIs(TokenTypeEnum.RPAREN)) {
            nextToken();
            return identifierList;
        }
        nextToken();
        Identifier firstIdf = new Identifier(currToken, currToken.getLiteral());
        identifierList.add(firstIdf);
        while (peekTokenIs(TokenTypeEnum.COMMA)) {
            nextToken();
            nextToken();
            Identifier postIdf = new Identifier(currToken, currToken.getLiteral());
            identifierList.add(postIdf);
        }
        if (!expectPeek(TokenTypeEnum.RPAREN)) {
            return null;
        }
        return identifierList;
    }
    public AbstractExpression parseIfExpression() {
        IfExpression expression = new IfExpression(currToken);
        if (!expectPeek(TokenTypeEnum.LPAREN)) {
            return null;
        }
        nextToken();
        expression.setCondition(parseExpression(PrecedenceEnum.LOWEST));
        if (!expectPeek(TokenTypeEnum.RPAREN)) {
            return null;
        }
        if (!expectPeek(TokenTypeEnum.LBRACE)) {
            return null;
        }
        expression.setConsequenceStatement(parseBlockStatement());
        if (peekTokenIs(TokenTypeEnum.ELSE)) {
            nextToken();
            if (!expectPeek(TokenTypeEnum.LBRACE)) {
                return null;
            }
            expression.setAlternativeStatement(parseBlockStatement());
        }
        return expression;
    }
    public BlockStatement parseBlockStatement() {
        BlockStatement statement = new BlockStatement(currToken);
        nextToken();
        while (!curTokenIs(TokenTypeEnum.RBRACE) && !curTokenIs(TokenTypeEnum.EOF)) {
            AbstractStatement subStatement = parseStatement();
            if (subStatement != null) {
                statement.getStatementList().add(subStatement);
            }
            nextToken();
        }
        return statement;
    }
    public AbstractExpression parseGroupedExpression() {
        nextToken();
        AbstractExpression expression = parseExpression(PrecedenceEnum.LOWEST);
        if (!expectPeek(TokenTypeEnum.RPAREN)) {
            return null;
        }
        return expression;
    }
    public AbstractExpression parseBoolean() {
        return new BooleanExpression(currToken, curTokenIs(TokenTypeEnum.TRUE));
    }
    public AbstractExpression parseIdentifier() {
        return new Identifier(currToken, currToken.getLiteral());
    }
    public AbstractExpression parseIntegerLiteral() {
        return new IntegerLiteral(currToken, Long.parseLong(currToken.getLiteral()));
    }
    public AbstractExpression parseStringLiteral() {
        return new StringLiteral(currToken, currToken.getLiteral());
    }
    public AbstractExpression parsePrefixExpression() {
        PrefixExpression expression = new PrefixExpression(currToken, currToken.getLiteral());
        nextToken();
        expression.setRight(parseExpression(PrecedenceEnum.PREFIX));
        return expression;
    }

    public AbstractExpression parseExpression(PrecedenceEnum precedence) {
        Method method = prefixFuns.get(currToken.getTokenType());
        if (method == null) {
            noPrefixParseFnError(currToken.getTokenType());
            return null;
        }
        AbstractExpression leftExp;
        try {
            leftExp = (AbstractExpression) method.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("get leftExp error {}", errors, e);
            return null;
        }
        while (!peekTokenIs(TokenTypeEnum.SEMICOLON) && precedence.lt(peekPrecedence())) {
            Method peekMethod = infixFuns.get(peekToken.getTokenType());
            if (peekMethod == null) {
                return leftExp;
            }
            nextToken();
            try {
                leftExp = (AbstractExpression) peekMethod.invoke(this, leftExp);
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error("while get leftExp {} error ", peekToken.toString(), e);
                return null;
            }
        }
        return leftExp;
    }

    private PrecedenceEnum peekPrecedence() {
        return tokenPrecedenceMap.getOrDefault(peekToken.getTokenType(), PrecedenceEnum.LOWEST);
    }

    private void noPrefixParseFnError(TokenTypeEnum typeEnum) {
        errors.add("no prefix parse function for "+Token.tokenTypeValue(typeEnum)+" found");
    }
    public Program parseProgram() {
        Program program = new Program();
        while (!curTokenIs(TokenTypeEnum.EOF)) {
            AbstractStatement abstractStatement = parseStatement();
            if (abstractStatement != null) {
                program.addStatement(abstractStatement);
            }
            nextToken();
        }
        return program;
    }

    boolean curTokenIs(TokenTypeEnum tokenTypeEnum) {
        return tokenTypeEnum.equalsCode(currToken.getTokenType());
    }

    public AbstractStatement parseStatement() {
        switch (currToken.getTokenType()) {
            case LET:
                return parseLetStatement();
            case RETURN:
                return parseReturnStatement();
            default:
                return parseExpressionStatement();
        }
    }

    private AbstractStatement parseExpressionStatement() {
        ExpressionStatement statement = new ExpressionStatement(currToken);
        AbstractExpression expression = parseExpression(PrecedenceEnum.LOWEST);
        if (expression == null) {
            return null;
        }
        statement.setExpression(expression);
        if (peekTokenIs(TokenTypeEnum.SEMICOLON)) {
            nextToken();
        }
        return statement;
    }

    private ReturnStatement parseReturnStatement() {
        ReturnStatement returnStatement = new ReturnStatement(currToken);
        nextToken();
        AbstractExpression expression = parseExpression(PrecedenceEnum.LOWEST);
        if (expression == null) {
            return null;
        }
        returnStatement.setReturnExpression(expression);
        if (peekTokenIs(TokenTypeEnum.SEMICOLON)) {
            nextToken();
        }
        return returnStatement;
    }

    public LetStatement parseLetStatement() {
        LetStatement statement = new LetStatement(currToken);
        if (!expectPeek(TokenTypeEnum.IDENT)) {
            return null;
        }
        statement.setName(new Identifier(currToken, currToken.getLiteral()));
        if (!expectPeek(TokenTypeEnum.ASSIGN)) {
            return null;
        }
        nextToken();
        AbstractExpression expression = parseExpression(PrecedenceEnum.LOWEST);
        if (expression == null) {
            return null;
        }
        statement.setValue(expression);
        if (peekTokenIs(TokenTypeEnum.SEMICOLON)) {
            nextToken();
        }
        return statement;
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
    private PrecedenceEnum curPrecedence() {
        PrecedenceEnum precedence = tokenPrecedenceMap.get(currToken.getTokenType());
        if (precedence == null) {
            return PrecedenceEnum.LOWEST;
        }
        return precedence;
    }
    private boolean peekTokenIs(TokenTypeEnum tokenTypeEnum) {
        return peekToken.getTokenType() == tokenTypeEnum;
    }
}
