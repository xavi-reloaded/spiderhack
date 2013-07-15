package com.socialintellegentia.processes;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.socialintellegentia.commonhelpers.restclient.SocialIntellegentiaAPI;
import com.socialintellegentia.commonhelpers.rss.Feed;
import com.socialintellegentia.commonhelpers.rss.FeedMessage;
import com.socialintellegentia.commonhelpers.rss.RSSFeedParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

    private static final String TOKEN = "9gLa3sIY2KR3G4YCBX7Qppi6zGvYOD0cAxzU2cnzb9o5YvxV4GGquD%252B4yCSnWp9o5ZQyi630NIyWt";
    protected Log log = LogFactory.getLog(this.getClass());
    protected DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MMMM-yyyy:HH:ss:mm");

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

    public String loadFeedInServer(Feed feed) throws Exception {


        DateTime dtBegin = new DateTime();
        log.debug("[ProcessRSS] --> Inject into server " + feed.getTitle() + " in: " + fmt.print(dtBegin));

        SocialIntellegentiaAPI siAPI = new SocialIntellegentiaAPI();
        String addFeed = siAPI.newsFeedService_addFeed(TOKEN, feed);

        DateTime dtEnd = new DateTime();
        Period totalPeriod = new Period(dtBegin, dtEnd, PeriodType.time());
        String strTotalTime=  totalPeriod.getHours() + ":" + totalPeriod.getSeconds() + ":" + totalPeriod.getMillis();
        String lastLog = "[ProcessRSS] --> Finish injection "+ feed.getTitle() +" in: " + fmt.print(dtEnd) +  ". Time: " + strTotalTime + "\n";

        log.debug(lastLog);

        return addFeed;
    }


}
