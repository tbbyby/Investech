package com.investech.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultTest {
    @Test
    void successShouldHaveCorrectData(){
        Result<String> result = Result.success("data");
        assertEquals("data", result.getData());
        assertEquals(200, result.getCode());
    }
    @Test
    void successShouldHaveCorrectDataAndMessage(){
        Result<String> result = Result.success("message", "data");
        assertEquals("message", result.getMessage());
        assertEquals("data", result.getData());
        assertEquals(200, result.getCode());
    }
    @Test
    void errorShouldHaveCorrectMessage(){
        Result<String> result = Result.error("message");
        assertEquals("message", result.getMessage());
        assertEquals(500, result.getCode());
    }
    @Test
    void errorShouldHaveCorrectCodeAndMessage(){
        Result<String> result = Result.error(400, "message");
        assertEquals("message", result.getMessage());
        assertEquals(400, result.getCode());
    }

}
