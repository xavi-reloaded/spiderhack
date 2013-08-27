package com.socialintellegentia.processes;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.socialintellegentia.commonhelpers.rss.Feed;
import com.socialintellegentia.commonhelpers.rss.FeedMessage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private File tempFolder;
    private String path;

    @BeforeMethod
    public void setUp() throws Exception {
        sut = new ProcessRSS();
        tempFolder = FileHelper.createTempFolder("spider_temp");
        path = tempFolder.getPath();
        setUpTempFiles();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        deleteTempFiles();

    }

    @Test
    public void test_indexFeedInSolr() throws Exception {

        Feed feed = exerciseGetFeedsFromSeedsByPath().get(2);
        FeedMessage feedMessage = feed.getFeedMessages().get(1);

        feedMessage.setTitle("This is a also a hardcoded title and Barcelona is a nice city where Pepe and Pepito works at Gromenauer Place");
        feedMessage.setAuthor("Xavi el Magnanimo");
        feedMessage.setGuid("11111111111111111111111111");
        feedMessage.setLink("http://www.lavanguardia.com/gente/20130827/54379808086/miley-cyrus-solivianta-eeuu-provocadora-sexual-mtv.html");

        ArrayList<FeedMessage> feedMessages = new ArrayList<FeedMessage>();
        feedMessages.add(feedMessage);
        feed.setFeedMessages(feedMessages);
        sut.indexFeedInSolr(feed);

    }

    @Test
    public void test_loadFeedInServer_() throws Exception {
        Feed feed = exerciseGetFeedsFromSeedsByPath().get(23);
        String serverResponse = sut.loadFeedInServer(feed);

        String expected = "{\"feeds\":";
        Assert.assertTrue(serverResponse.startsWith(expected));

    }

    @Test
    public void test_getFeedsFromSeedsByPath_validPathWithRealFeeds_listOfRSSFrontEndHelpersCorrectlyPopulated() throws Exception
    {
        List<Feed> actual = exerciseGetFeedsFromSeedsByPath();
        int expected = 73;
        Assert.assertEquals(actual.size(),expected);
    }

    private List<Feed> exerciseGetFeedsFromSeedsByPath() throws Exception {
        return sut.getFeedsFromSeedsByPath(path);
    }

    @Test
    public void test_getFeedsFromSeedsByPath_validPathWithRealFeeds_feedCorrectlyPopulateMessages() throws Exception
    {
        Feed actual = exerciseGetFeedsFromSeedsByPath().get(23);
        Assert.assertTrue(!actual.getTitle().isEmpty());
    }

    @Test
    public void test_getFeedsFromSeedsByPath_validPathWithRealFeeds_seedsAreCorrectlyDeleted() throws Exception
    {

        exerciseGetFeedsFromSeedsByPath();
        List<String> fileList = FileHelper.getFileList(path, "");
        int expected = 0;
        Assert.assertEquals(fileList.size(),expected);
    }

    private void deleteTempFiles() throws IOException {
        FileHelper.recursivelyDeleteFileOrFolder(new File(tempFolder.getPath()));
    }

    private void setUpTempFiles() throws IOException {

        String path = ProcessRSSTest.class.getResource("/SpiderSiRSS").getPath();
        List<String> fileList = FileHelper.getFileList(path, "");
        for(String filename : fileList)
        {
            FileHelper.copyfile(filename, tempFolder.getPath() + "/" + new File(filename).getName());
        }

    }



}
