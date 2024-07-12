package com.wkr.tp;

import com.wkr.tp.ast.Program;
import com.wkr.tp.object.*;
import com.wkr.tp.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;
import org.javatuples.Tuple;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangkun1-jk
 * @Description:
 * @date 2024/7/8 19:31
 */
public class EvalTest {
    private static final Logger logger = LogManager.getLogger(EvalTest.class);
    private RtObject testVal(String script) {
        Lexer l = new Lexer(script);
        Parser p = new Parser(l);
        Program program = p.parseProgram();
        Evaluator evaluator = new Evaluator();
        return evaluator.eval(program, new Environment());
    }
    @Test
    public void integerExpressionTest() {
        List<Pair<String, Long>> scriptList = new ArrayList<>();
        scriptList.add(Pair.with("5", 5L));
        scriptList.add(Pair.with("10", 10L));
        scriptList.add(Pair.with("-5", -5L));
        scriptList.add(Pair.with("-10", -10L));
        scriptList.add(Pair.with("5 + 5 + 5 + 5 - 10", 10L));
        scriptList.add(Pair.with("2 * 2 * 2 * 2 * 2", 32L));
        scriptList.add(Pair.with("-50 + 100 + -50", 0L));
        scriptList.add(Pair.with("5 * 2 + 10", 20L));
        scriptList.add(Pair.with("5 + 2 * 10", 25L));
        scriptList.add(Pair.with("20 + 2 * -10", 0L));
        scriptList.add(Pair.with("50 / 2 * 2 + 10", 60L));
        scriptList.add(Pair.with("2 * (5 + 10)", 30L));
        scriptList.add(Pair.with("3 * 3 * 3 + 10", 37L));
        scriptList.add(Pair.with("3 * (3 * 3) + 10", 37L));
        scriptList.add(Pair.with("(5 + 10 * 2 + 15 / 3) * 2 + -10", 50L));
        for (Pair<String, Long> sc : scriptList) {
            RtObject val = testVal(sc.getValue0());
            Assert.assertEquals(((RtInteger) val).getValue(), (long) sc.getValue1());
        }
    }
    @Test
    public void booleanExpressionTest() {
        List<Pair<String, Boolean>> scriptList = new ArrayList<>();
        scriptList.add(Pair.with("true", true));
        scriptList.add(Pair.with("false", false));
        scriptList.add(Pair.with("1 < 2", true));
        scriptList.add(Pair.with("1 > 2", false));
        scriptList.add(Pair.with("1 < 1", false));
        scriptList.add(Pair.with("1 > 1", false));
        scriptList.add(Pair.with("1 == 1", true));
        scriptList.add(Pair.with("1 != 1", false));
        scriptList.add(Pair.with("1 == 2", false));
        scriptList.add(Pair.with("1 != 2", true));
        scriptList.add(Pair.with("true == true", true));
        scriptList.add(Pair.with("false == false", true));
        scriptList.add(Pair.with("true == false", false));
        scriptList.add(Pair.with("true != false", true));
        scriptList.add(Pair.with("false != true", true));
        scriptList.add(Pair.with("(1 < 2) == true", true));
        scriptList.add(Pair.with("(1 < 2) == false", false));
        scriptList.add(Pair.with("(1 > 2) == true", false));
        scriptList.add(Pair.with("(1 > 2) == false", true));
        for (Pair<String, Boolean> sc : scriptList) {
            RtObject val = testVal(sc.getValue0());
            Assert.assertEquals(((RtBoolean) val).isValue(), sc.getValue1());
        }
    }
    @Test
    public void bangExpressionTest() {
        List<Pair<String, Boolean>> scriptList = new ArrayList<>();
        scriptList.add(Pair.with("!true", false));
        scriptList.add(Pair.with("!false", true));
        scriptList.add(Pair.with("!5", false));
        scriptList.add(Pair.with("!!true", true));
        scriptList.add(Pair.with("!!false", false));
        scriptList.add(Pair.with("!!5", true));
        for (Pair<String, Boolean> sc : scriptList) {
            RtObject val = testVal(sc.getValue0());
            Assert.assertEquals(((RtBoolean) val).isValue(), sc.getValue1());
        }
    }
    @Test
    public void ifExpressionTest() {
        List<Pair<String, Integer>> scriptList = new ArrayList<>();
        scriptList.add(Pair.with("if (true) { 10 }", 10));
        scriptList.add(Pair.with("if (false) { 10 }", null));
        scriptList.add(Pair.with("if (1) { 10 }", 10));
        scriptList.add(Pair.with("if (1 < 2) { 10 }", 10));
        scriptList.add(Pair.with("if (1 > 2) { 10 }", null));
        scriptList.add(Pair.with("if (1 > 2) { 10 } else { 20 }", 20));
        scriptList.add(Pair.with("if (1 < 2) { 10 } else { 20 }", 10));
        for (Pair<String, Integer> sc : scriptList) {
            RtObject val = testVal(sc.getValue0());
            if (val instanceof  RtInteger) {
                Assert.assertEquals(((RtInteger) val).getValue(), (long)sc.getValue1());
            } else if (val instanceof RtNull) {
                Assert.assertNull(sc.getValue1());
            } else {
                logger.info("val失败: {}", val.inspect());
            }
        }
    }
    @Test
    public void returnStatementTest() {
        List<Pair<String, Integer>> scriptList = new ArrayList<>();
        scriptList.add(Pair.with("return 10;", 10));
        scriptList.add(Pair.with("return 10; 9;", 10));
        scriptList.add(Pair.with("return 2 * 5; 9;", 10));
        scriptList.add(Pair.with("9; return 2 * 5; 9;", 10));
        scriptList.add(Pair.with("if (10 > 1) { return 10; }", 10));
        scriptList.add(Pair.with("(\n" +
                "if (10 > 1) {\n" +
                "  if (10 > 1) {\n" +
                "    return 10;\n" +
                "  }\n" +
                "\n" +
                "  return 1;\n" +
                "}\n" +
                ")", 10));
//        scriptList.add(Pair.with("(\n" +
//                "let f = fn(x) {\n" +
//                "  return x;\n" +
//                "  x + 10;\n" +
//                "};\n" +
//                "f(10);)", 10));
//        scriptList.add(Pair.with("(\n" +
//                "let f = fn(x) {\n" +
//                "   let result = x + 10;\n" +
//                "   return result;\n" +
//                "   return 10;\n" +
//                "};\n" +
//                "f(10);)", 20));

        for (Pair<String, Integer> sc : scriptList) {
            logger.info("test: {}", sc.getValue0());
            RtObject val = testVal(sc.getValue0());
            if (val instanceof RtInteger) {
                Assert.assertEquals(((RtInteger) val).getValue(), (long)sc.getValue1());
            } else {
                logger.error("val失败: {}", val.inspect());
            }
        }
    }
    @Test
    public void errorHandlingTest() {
        List<Pair<String, String>> scriptList = new ArrayList<>();
        scriptList.add(Pair.with("5 + true;","type mismatch: INTEGER + BOOLEAN"));
        scriptList.add(Pair.with("5 + true; 5;","type mismatch: INTEGER + BOOLEAN"));
        scriptList.add(Pair.with("-true","unknown operator: -BOOLEAN"));
        scriptList.add(Pair.with("true + false;","unknown operator: BOOLEAN + BOOLEAN"));
        scriptList.add(Pair.with("true + false + true + false;","unknown operator: BOOLEAN + BOOLEAN"));
        scriptList.add(Pair.with("5; true + false; 5","unknown operator: BOOLEAN + BOOLEAN"));
        scriptList.add(Pair.with("if (10 > 1) { true + false; }","unknown operator: BOOLEAN + BOOLEAN"));
        scriptList.add(Pair.with("(\"Hello\" - \"World\" )","unknown operator: STRING - STRING"));
        scriptList.add(Pair.with("foobar","identifier not found: foobar"));
        scriptList.add(Pair.with("(\n" +
                "if (10 > 1) {\n" +
                "   if (10 > 1) {\n" +
                "       return true + false;\n" +
                "   }\n" +
                "\n" +
                "   return 1;\n" +
                "})","unknown operator: BOOLEAN + BOOLEAN"));
        for (Pair<String, String> sc : scriptList) {
            logger.info("test: {}", sc.getValue0());
            RtObject val = testVal(sc.getValue0());
            if (val instanceof RtError) {
                Assert.assertEquals(((RtError) val).getMessage(), sc.getValue1());
            } else {
                logger.error("val失败: {}", val.inspect());
            }
        }
    }
    @Test
    public void letStatementTest() {
        List<Pair<String, Long>> scriptList = new ArrayList<>();
        scriptList.add(Pair.with("let a = 5; a;", 5L));
        scriptList.add(Pair.with("let a = 5 * 5; a;", 25L));
        scriptList.add(Pair.with("let a = 5; let b = a; b;", 5L));
        scriptList.add(Pair.with("let a = 5; let b = a; let c = a + b + 5; c;", 15L));
        for (Pair<String, Long> sc : scriptList) {
            logger.info("test: {}", sc.getValue0());
            RtObject val = testVal(sc.getValue0());
            if (val instanceof RtInteger) {
                Assert.assertEquals(((RtInteger) val).getValue(), (long)sc.getValue1());
            } else {
                logger.error("val失败: {}", val.inspect());
            }
        }
    }
    @Test
    public void functionObjectTest() {
        String input = "fn(x) { x + 2; };";
        RtObject val = testVal(input);
        RtFunction function = (RtFunction)val;
        Assert.assertEquals(function.getParams().size(), 1);
        Assert.assertEquals(function.getParams().get(0).getValue(), "x");
        Assert.assertEquals(function.getBody().toString(), "(x + 2)");
    }
    @Test
    public void functionApplicationTest() {
        List<Pair<String, Integer>> scriptList = new ArrayList<>();
        scriptList.add(Pair.with("let identity = fn(x) { x; }; identity(5);", 5));
        scriptList.add(Pair.with("let identity = fn(x) { return x; }; identity(5);", 5));
        scriptList.add(Pair.with("let double = fn(x) { x * 2; }; double(5);", 10));
        scriptList.add(Pair.with("let add = fn(x, y) { x + y; }; add(5, 5);", 10));
        scriptList.add(Pair.with("let add = fn(x, y) { x + y; }; add(5 + 5, add(5, 5));", 20));
        scriptList.add(Pair.with("fn(x) { x; }(5)", 5));
        for (Pair<String, Integer> sc : scriptList) {
            logger.info("test: {}", sc.getValue0());
            RtObject val = testVal(sc.getValue0());
            if (val instanceof RtInteger) {
                Assert.assertEquals(((RtInteger) val).getValue(), (long)sc.getValue1());
            } else {
                logger.error("val失败: {}", val.inspect());
            }
        }
    }
    @Test
    public void enclosingEnvTest() {
        String input = "(\n" +
                "        let first = 10;\n" +
                "        let second = 10;\n" +
                "        let third = 10;\n" +
                "\n" +
                "        let ourFunction = fn(first) {\n" +
                "            let second = 20;\n" +
                "\n" +
                "            first + second + third;\n" +
                "        };\n" +
                "\n" +
                "        ourFunction(20) + first + second;\n" +
                "\t)";

        RtObject val = testVal(input);
        Assert.assertEquals(((RtInteger) val).getValue(), 70);
    }
}
