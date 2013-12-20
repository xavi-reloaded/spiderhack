package com.socialintellegentia.util;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by xavi on 12/20/13.
 */
public class UniqueFeedHandlerTest {


    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void test_getSingleton() throws Exception {
        UniqueFeedHandler sut = UniqueFeedHandler.getInstance();
        Assert.assertNotNull(sut);
    }

    @Test
    public void test_getSingleton_twice() throws Exception {
        UniqueFeedHandler sut = UniqueFeedHandler.getInstance();
        UniqueFeedHandler sut2 = UniqueFeedHandler.getInstance();
        Assert.assertSame(sut, sut2);
    }

    @Test
    public void test_put_fakeIndex() throws Exception {
        UniqueFeedHandler sut = UniqueFeedHandler.getInstance();
        sut.put("this is my index, the one and only");
    }

    @Test(dependsOnMethods = "test_put_fakeIndex")
    public void test_exists_existingIndex_true() throws Exception {
        boolean actual = exerciseExists("this is my index, the one and only");
        Assert.assertTrue(actual);
    }

    @Test
    public void test_exists_nonExistingIndex_false() throws Exception {
        boolean actual = exerciseExists("asdfasdfdasdfasdfas");
        Assert.assertFalse(actual);
    }



    private boolean exerciseExists(String feedGui) {
        UniqueFeedHandler sut = UniqueFeedHandler.getInstance();
        return sut.exists(feedGui);
    }

}
