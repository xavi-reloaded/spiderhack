package com.socialintellegentia.processes;

import com.socialintellegentia.commonhelpers.rss.Feed;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sidev
 * Date: 7/12/13
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessRSSTest {

    private ProcessRSS sut;

    @BeforeMethod
    public void setUp() throws Exception {
        sut = new ProcessRSS();
    }

    @Test
    public void test_getObjectsFromGettedFeeds_validPathWithRealFeeds_listOfRSSFrontEndHelpersCorrectlyPopulated() throws Exception {
        String path = ProcessRSSTest.class.getResource("/SpiderSiRSS/").getPath();
        List<Feed> actual = sut.getfeedObjectsFromGettedFeedsFiles(path);
        int expected = 73;
        Assert.assertEquals(actual.size(),expected);
    }
}
