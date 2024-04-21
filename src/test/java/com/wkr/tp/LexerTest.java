package com.wkr.tp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;



public class LexerTest {
    Logger logger = LogManager.getLogger(LexerTest.class);
    @Test
    public void basicTest() {
        String first = "int age = 3;";
        logger.info("first test {}", first);
        Lexer lexer = new Lexer(first);

    }
}
