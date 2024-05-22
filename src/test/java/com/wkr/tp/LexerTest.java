package com.wkr.tp;

import com.wkr.tp.enums.TokenTypeEnum;
import com.wkr.tp.token.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class LexerTest {
    Logger logger = LogManager.getLogger(LexerTest.class);
    @Test
    public void basicTest() {
        String first = "int age = 3;age+1;int next = age+1";
        logger.info("first test {}", first);
        Lexer lexer = new Lexer(first);
        while (true) {
            Token token = lexer.nextToken();
            if (token.getTokenType().equalsCode(TokenTypeEnum.EOF)) {
                break;
            }
            logger.info(token.toString());
        }
    }
}
