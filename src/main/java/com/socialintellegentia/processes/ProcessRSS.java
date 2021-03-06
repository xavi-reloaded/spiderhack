package com.socialintellegentia.processes;

import com.androidxtrem.commonsHelpers.FileHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sidev
 * Date: 7/12/13
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessRSS {

    private static final String TOKEN = "9gLa3sIY2KR3G4YCBX7Qppi6zGvYOD0cAxzU2cnzb9o5YvxV4GGquD%252B4yCSnWp9o5ZQyi630NIyWt";

    private boolean keepCacheFiles = false;
    protected Log log = LogFactory.getLog(ProcessRSS.class);
    protected DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MMMM-yyyy:HH:ss:mm");
//
//    private CustomFeedParser feedParser;
//
//
//
//    public ProcessRSS() {
//
//        this.keepCacheFiles = keepCacheFiles;
//    }
//
//
//    public Integer processRSSfromWorkingDirectory(String workingFolder) throws Exception {
//        Integer feedMessageProcessed=0;
//        DateTime dtBegin = new DateTime();
//        log.debug("[ProcessRSS] --> Begin process injection in " + workingFolder + " in: " + fmt.print(dtBegin));
//
//        List<Feed> feeds = getFeedsFromSeedsByPath(workingFolder);
//
//        log.debug("[ProcessRSS] --> Catch process injection with: [" + feeds.size() + "] Feeds");
//
//        for (Feed feed : feeds) {
//            if (feed==null) continue;
//            if (!feed.getFeedMessages().isEmpty()) {
//                indexFeedInSolr(feed);
//                feedMessageProcessed=feed.getFeedMessages().size();
//            }
//        }
//
//        DateTime dtEnd = new DateTime();
//        Period totalPeriod = new Period(dtBegin, dtEnd, PeriodType.time());
//        String strTotalTime=  totalPeriod.getHours() + ":" + totalPeriod.getSeconds() + ":" + totalPeriod.getMillis();
//        log.debug("[ProcessRSS] --> Finish process injection in " + workingFolder + " in: " + fmt.print(dtEnd) +  ". Time: " + strTotalTime + "\n");
//
//        return feedMessageProcessed;
//    }
//
//    public List<Feed> getFeedsFromSeedsByPath(String path) throws Exception {
//        List<String> fileList = (new File(path).isDirectory()) ? FileHelper.getFileList(path, "") : Arrays.asList(new String[]{path});
//        feedParser = new CustomFeedParser();
//        List<Feed> feedList = new ArrayList<Feed>();
//
//        for(String filePath : fileList)
//        {
//            String fileString = FileHelper.fileToString(filePath);
//            Feed feed = feedParser.parse(filePath, fileString.getBytes());
//            if (feed!=null) feedList.add(feed);
//            if (!keepCacheFiles) {
//                File file = new File(filePath);
//                if (file.canWrite()) {
//                    FileHelper.deleteFile(file);
//                }
//            }
//        }
//        return feedList;
//    }
//
//    public String loadFeedInServer(Feed feed) throws Exception {
//
//        if (feed==null) {
//            log.error("[ProcessRSS] --> Feed is null ");
//            return "";
//        }
//
//        DateTime dtBegin = new DateTime();
//        String title = feed.getTitle();
//        log.debug("[ProcessRSS] --> Inject into server " + title + " in: " + fmt.print(dtBegin));
//
//
//        String addFeed = siAPI.newsFeedService_addFeed(TOKEN, feed);
//
//        log.debug("[ProcessRSS] --> Server response: [" + addFeed + "]");
//        log.debug("[ProcessRSS] --> With : [" + feed.toJson() + "]");
//        DateTime dtEnd = new DateTime();
//        Period totalPeriod = new Period(dtBegin, dtEnd, PeriodType.time());
//        String strTotalTime=  totalPeriod.getHours() + ":" + totalPeriod.getSeconds() + ":" + totalPeriod.getMillis();
//        log.debug("[ProcessRSS] --> Finish injection "+ addFeed +" in: " + fmt.print(dtEnd) +  ". Time: " + strTotalTime + "\n");
//
//        return addFeed;
//    }

    public void setKeepCacheFiles(boolean keepCacheFiles) {
        this.keepCacheFiles = keepCacheFiles;
    }
}
