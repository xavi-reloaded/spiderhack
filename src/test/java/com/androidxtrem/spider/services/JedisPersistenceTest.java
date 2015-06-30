package com.androidxtrem.spider.services;


import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class JedisPersistenceTest {

    private JedisPersistence sut;

    @BeforeMethod
    public void setUp() throws Exception {
        sut = new JedisPersistence();
        sut.reset();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        sut.reset();

    }

    @Test
    public void test_AddMessageToQueue() throws Exception {
        int actual = sut.set("this is a test message");
        int expected = 1;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void test_get_existing() throws Exception {
        sut.set("this is a test message");
        String actual = sut.get("this is a test message");
        String expected = "blacklist";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void test_get_nonexisting() throws Exception {
        String actual = sut.get("I simply don't exist");
        String expected = "";
        Assert.assertEquals(actual, expected);
    }
}