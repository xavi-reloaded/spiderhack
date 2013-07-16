package com.socialintellegentia.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sidev
 * Date: 7/15/13
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonHelperTest {
    @Test
    public void testName() throws Exception {
        String json = "[{\"source\":\"weke\"},{\"source\":\"weka\"},{\"source\":\"NOTHING\"},{\"source\":\" 12345\"}]";
        List<String> actual = JsonHelper.getRssSourcesFromJson(json);
        String expected = "weka";
        Assert.assertEquals(actual.get(1), expected );


    }
}
