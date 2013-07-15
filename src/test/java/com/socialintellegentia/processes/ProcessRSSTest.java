package com.socialintellegentia.processes;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.socialintellegentia.commonhelpers.rss.Feed;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
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
    public void test_loadFeedInServer_() throws Exception {
        Feed feed = sut.getFeedsFromSeedsByPath(path).get(23);
        String serverResponse = sut.loadFeedInServer(feed);

        String expected = "{\"feeds\":";
        Assert.assertTrue(serverResponse.startsWith(expected));

    }

    @Test
    public void test_getFeedsFromSeedsByPath_validPathWithRealFeeds_listOfRSSFrontEndHelpersCorrectlyPopulated() throws Exception
    {
        List<Feed> actual = sut.getFeedsFromSeedsByPath(path);
        int expected = 73;
        Assert.assertEquals(actual.size(),expected);
    }

    @Test
    public void test_getFeedsFromSeedsByPath_validPathWithRealFeeds_feedCorrectlyPopulateMessages() throws Exception
    {
        Feed actual = sut.getFeedsFromSeedsByPath(path).get(23);
        Assert.assertTrue(!actual.getTitle().isEmpty());
    }

    @Test
    public void test_getFeedsFromSeedsByPath_validPathWithRealFeeds_seedsAreCorrectlyDeleted() throws Exception
    {

        sut.getFeedsFromSeedsByPath(path);
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
