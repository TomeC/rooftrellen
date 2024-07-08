package com.wkr.tp;

import com.wkr.tp.ast.expression.FunctionLiteral;
import com.wkr.tp.ast.expression.Identifier;
import com.wkr.tp.ast.expression.IntegerLiteral;
import com.wkr.tp.ast.Program;
import com.wkr.tp.ast.expression.BooleanExpression;
import com.wkr.tp.ast.expression.IfExpression;
import com.wkr.tp.ast.expression.InfixExpression;
import com.wkr.tp.ast.expression.PrefixExpression;
import com.wkr.tp.ast.statement.ExpressionStatement;
import com.wkr.tp.ast.statement.LetStatement;
import com.wkr.tp.ast.statement.ReturnStatement;
import com.wkr.tp.enums.TokenTypeEnum;
import com.wkr.tp.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wkr
 * @description
 * @date 2024/6/10
 */
public class ParserTest {
    Logger logger = LogManager.getLogger(ParserTest.class);

    private Program parseProgram(String script) throws Exception {
        Parser parser = new Parser(script);
        if (parser.getErrors().size() > 0) {
            parser.getErrors().stream().forEach(item -> logger.info(item));
            throw new Exception("parse error");
        }
        Program program = parser.parseProgram();
        return program;
    }

    @Test
    public void letStatementsTest() throws Exception {
        {
            String sc = "let x = 5;";
            Program program = parseProgram(sc);
            LetStatement letStatement = (LetStatement) program.getStatementList().get(0);
            logger.info("letStatement={}", letStatement);
            Assert.assertEquals(letStatement.getToken().getLiteral(), "let");
            Assert.assertEquals(letStatement.getName().getTokenLiteral(), "x");
            Assert.assertEquals(((IntegerLiteral)(letStatement.getValue())).getValue(), 5L);
        }
        {
            String sc = "let y = x;";
            Program program = parseProgram(sc);
            LetStatement letStatement = (LetStatement) program.getStatementList().get(0);
            logger.info("letStatement={}", letStatement);
            Assert.assertEquals(letStatement.getToken().getLiteral(), "let");
            Assert.assertEquals(letStatement.getName().getValue(), "y");
            Assert.assertEquals(((Identifier)letStatement.getValue()).getValue(), "x");
        }
        {
            String sc = "let y = true;";
            Program program = parseProgram(sc);
            LetStatement letStatement = (LetStatement) program.getStatementList().get(0);
            logger.info("letStatement={}", letStatement);
            Assert.assertEquals(letStatement.getToken().getLiteral(), "let");
            Assert.assertEquals(letStatement.getName().getValue(), "y");
            Assert.assertEquals(((BooleanExpression)letStatement.getValue()).isValue(), true);
        }
    }
    @Test
    public void returnStatementTest() throws Exception {
        {
            String script = "return 5;";
            Program program = parseProgram(script);
            ReturnStatement statement = (ReturnStatement) program.getStatementList().get(0);
            logger.info("ReturnStatement={}", statement);
            Assert.assertEquals(statement.getToken().getLiteral(), "return");
            Assert.assertEquals(((IntegerLiteral)(statement.getReturnExpression())).getValue(), 5L);
        }
        {
            String script = "return x;";
            Program program = parseProgram(script);
            ReturnStatement statement = (ReturnStatement) program.getStatementList().get(0);
            logger.info("ReturnStatement={}", statement);
            Assert.assertEquals(statement.getToken().getLiteral(), "return");
            Assert.assertEquals(((Identifier)(statement.getReturnExpression())).getValue(), "x");
        }
    }
    @Test
    public void identifierTest() throws Exception {
        {
            String script = "name;";
            Program program = parseProgram(script);
            ExpressionStatement statement = (ExpressionStatement) program.getStatementList().get(0);
            Identifier identifier = (Identifier) statement.getExpression();
            logger.info("identifier={}", identifier);
            Assert.assertEquals(identifier.getValue(), "name");
        }

    }
    @Test
    public void integerLiteralTest() throws Exception {
        {
            String script = "5;";
            Program program = parseProgram(script);
            ExpressionStatement statement = (ExpressionStatement) program.getStatementList().get(0);
            IntegerLiteral integerLiteral = (IntegerLiteral) statement.getExpression();
            logger.info("identifier={}", integerLiteral);
            Assert.assertEquals(integerLiteral.getValue(), 5L);
        }
    }
    @Test
    public void prefixExpTest() throws Exception {
        {
            String script = "-5;";
            Program program = parseProgram(script);
            logger.info("program={}", program);
            ExpressionStatement statement = (ExpressionStatement) program.getStatementList().get(0);
            PrefixExpression expression = (PrefixExpression) statement.getExpression();
            Assert.assertEquals(expression.getOperator(), "-");
            Assert.assertEquals(((IntegerLiteral)expression.getRight()).getValue(), 5L);
        }
        {
            String script = "!true;";
            Program program = parseProgram(script);
            logger.info("program={}", program);
            ExpressionStatement statement = (ExpressionStatement) program.getStatementList().get(0);
            PrefixExpression expression = (PrefixExpression) statement.getExpression();
            Assert.assertEquals(expression.getOperator(), "!");
            Assert.assertEquals(((BooleanExpression)expression.getRight()).isValue(), true);
        }
    }
    @Test
    public void infixExpTest() throws Exception {
        {
            String script = "1+2;";
            Program program = parseProgram(script);
            logger.info("program={}", program);
            ExpressionStatement statement = (ExpressionStatement) program.getStatementList().get(0);
            InfixExpression expression = (InfixExpression) statement.getExpression();
            Assert.assertEquals(expression.getOperator(), "+");
            Assert.assertEquals(((IntegerLiteral)expression.getLeftExp()).getValue(), 1L);
            Assert.assertEquals(((IntegerLiteral)expression.getRightExp()).getValue(), 2L);

        }
        {
            String script = "x* y;";
            Program program = parseProgram(script);
            logger.info("program={}", program);
            ExpressionStatement statement = (ExpressionStatement) program.getStatementList().get(0);
            InfixExpression expression = (InfixExpression) statement.getExpression();
            Assert.assertEquals(expression.getOperator(), "*");
            Assert.assertEquals(((Identifier)expression.getLeftExp()).getValue(), "x");
            Assert.assertEquals(((Identifier)expression.getRightExp()).getValue(), "y");
        }
        {
            String script = "true != false;";
            Program program = parseProgram(script);
            logger.info("program={}", program);
            ExpressionStatement statement = (ExpressionStatement) program.getStatementList().get(0);
            InfixExpression expression = (InfixExpression) statement.getExpression();
            Assert.assertEquals(expression.getOperator(), "!=");
            Assert.assertEquals(expression.getToken().getTokenType(), TokenTypeEnum.NOT_EQ);
            Assert.assertEquals(((BooleanExpression)expression.getLeftExp()).isValue(), true);
            Assert.assertEquals(((BooleanExpression)expression.getRightExp()).isValue(), false);
        }
    }
    @Test
    public void operatorPrecedenceParseTest() throws Exception {
        List<Pair<String, String>> scripts = new ArrayList<>();
        scripts.add(new Pair<>("-a*b", "((-a) * b)"));
        scripts.add(new Pair<>( "!-a", "(!(-a))"));
        scripts.add(new Pair<>("a + b + c","((a + b) + c)"));
        scripts.add(new Pair<>("a + b - c","((a + b) - c)"));
        scripts.add(new Pair<>("a * b * c","((a * b) * c)"));
        scripts.add(new Pair<>("a * b / c","((a * b) / c)"));
        scripts.add(new Pair<>("a + b / c","(a + (b / c))"));
        scripts.add(new Pair<>("a + b * c + d / e - f","(((a + (b * c)) + (d / e)) - f)"));
        scripts.add(new Pair<>("3 + 4; -5 * 5","(3 + 4)((-5) * 5)"));
        scripts.add(new Pair<>("5 > 4 == 3 < 4","((5 > 4) == (3 < 4))"));
        scripts.add(new Pair<>("5 < 4 != 3 > 4","((5 < 4) != (3 > 4))"));
        scripts.add(new Pair<>("3 + 4 * 5 == 3 * 1 + 4 * 5","((3 + (4 * 5)) == ((3 * 1) + (4 * 5)))"));
        scripts.add(new Pair<>("true","true"));
        scripts.add(new Pair<>("false","false"));
        scripts.add(new Pair<>("3 > 5 == false","((3 > 5) == false)"));
        scripts.add(new Pair<>("3 < 5 == true","((3 < 5) == true)"));
        scripts.add(new Pair<>("1 + (2 + 3) + 4","((1 + (2 + 3)) + 4)"));
        scripts.add(new Pair<>("(5 + 5) * 2","((5 + 5) * 2)"));
        scripts.add(new Pair<>("2 / (5 + 5)","(2 / (5 + 5))"));
        scripts.add(new Pair<>("(5 + 5) * 2 * (5 + 5)","(((5 + 5) * 2) * (5 + 5))"));
        scripts.add(new Pair<>("-(5 + 5)","(-(5 + 5))"));
        scripts.add(new Pair<>("!(true == true)","(!(true == true))"));
        scripts.add(new Pair<>("1*2+3", "((1 * 2) + 3)"));
        scripts.add(new Pair<>("a + add(b * c) + d","((a + add((b * c))) + d)"));
        scripts.add(new Pair<>("add(a, b, 1, 2 * 3, 4 + 5, add(6, 7 * 8))","add(a, b, 1, (2 * 3), (4 + 5), add(6, (7 * 8)))"));
        scripts.add(new Pair<>("add(a + b + c * d / f + g)","add((((a + b) + ((c * d) / f)) + g))"));
        scripts.add(new Pair<>("a * [1, 2, 3, 4][b * c] * d","((a * ([1, 2, 3, 4][(b * c)])) * d)"));
        scripts.add(new Pair<>("add(a * b[2], b[1], 2 * [1, 2][1])","add((a * (b[2])), (b[1]), (2 * ([1, 2][1])))"));
        for (Pair<String, String> pair : scripts) {
            Program program = parseProgram(pair.getValue0());
            logger.info("program={}", program);
            Assert.assertEquals(program.toString(), pair.getValue1());
        }
    }
    @Test
    public void boolLiteralTest() throws Exception {
        List<Pair<String, Boolean>> scripts = new ArrayList<>();
        scripts.add(new Pair<>("true", true));
        scripts.add(new Pair<>("false", false));

        for (Pair<String, Boolean> pair : scripts) {
            Program program = parseProgram(pair.getValue0());
            logger.info("program={}", program);
            ExpressionStatement expression = (ExpressionStatement) program.getStatementList().get(0);
            Assert.assertEquals(((BooleanExpression)expression.getExpression()).isValue(), pair.getValue1());
        }
    }
    @Test
    public void ifExpressionTest() throws Exception {
        {
            String script = "if (x<y) {y} else {x}";
            Program program = parseProgram(script);
            logger.info("program={}", program);
            IfExpression expression = (IfExpression) ((ExpressionStatement)program.getStatementList().get(0)).getExpression();
            Assert.assertEquals(((InfixExpression)expression.getCondition()).getLeftExp().getTokenLiteral(), "x");
            Assert.assertEquals(((InfixExpression)expression.getCondition()).getOperator(), "<");
            Assert.assertEquals(((InfixExpression)expression.getCondition()).getRightExp().getTokenLiteral(), "y");

            Assert.assertEquals(((Identifier)((ExpressionStatement)expression.getConsequenceStatement().getStatementList().get(0)).getExpression()).getValue(), "y");
            Assert.assertEquals(((Identifier)((ExpressionStatement)expression.getAlternativeStatement().getStatementList().get(0)).getExpression()).getValue(), "x");
        }
    }
    @Test
    public void functionLiteralTest() throws Exception {
        String script = "fn(x, y) {x+y;}";
        Program program = parseProgram(script);
        logger.info("program={}", program);
        FunctionLiteral functionLiteral = (FunctionLiteral) ((ExpressionStatement)program.getStatementList().get(0)).getExpression();
        Assert.assertEquals(functionLiteral.getParameterList().get(0).getValue(), "x");
        Assert.assertEquals(functionLiteral.getParameterList().get(1).getValue(), "y");

        Assert.assertEquals(functionLiteral.getBody().getStatementList().get(0).toString(), "(x + y)");
    }

}
