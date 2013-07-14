package com.socialintellegentia.processes;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.socialintellegentia.commonhelpers.rss.Feed;
import com.socialintellegentia.commonhelpers.rss.FeedMessage;
import com.socialintellegentia.commonhelpers.rss.RSSFeedParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sidev
 * Date: 7/12/13
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessRSS {

    public List<Feed> getFeedsFromSeedsByPath(String path) throws Exception {
        List<String> fileList = FileHelper.getFileList(path, "");
        RSSFeedParser parser = new RSSFeedParser();
        List<Feed> feedList = new ArrayList<Feed>();

        for(String filePath : fileList)
        {
            String fileString = FileHelper.fileToString(filePath);
            Feed feed = parser.readFeed(fileString);
            feedList.add(feed);
            FileHelper.deleteFile(filePath);
        }

        return feedList;
    }

    public String loadFeedInServer(Feed feed) {
        List<FeedMessage> feedMessages = feed.getMessages();

        for(FeedMessage feedMessage : feedMessages)
        {
            System.out.println(feedMessage.toString());
        }

        return null;  //To change body of created methods use File | Settings | File Templates.
    }


}
