package com.socialintellegentia.processes;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.nlp.ner.FreeLingEnglishBioNerNer;
import com.androidxtrem.nlp.ner.FreeLingSpanishBioNerNer;
import com.androidxtrem.nlp.ner.INamedEntityRecognizer;
import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
import com.socialintellegentia.commonhelpers.restclient.SocialIntellegentiaAPI;
import com.socialintellegentia.commonhelpers.rss.Feed;
import com.socialintellegentia.commonhelpers.rss.FeedMessage;
import com.socialintellegentia.commonhelpers.rss.RSSFeedParser;
import com.socialintellegentia.solr.SolrHelper;
import com.socialintellegentia.solr.SolrIndexer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.common.SolrInputDocument;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: sidev
 * Date: 7/12/13
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessRSS {

    private static final String TOKEN = "9gLa3sIY2KR3G4YCBX7Qppi6zGvYOD0cAxzU2cnzb9o5YvxV4GGquD%252B4yCSnWp9o5ZQyi630NIyWt";
    private static final String QUERYING_URL = "http://localhost:8983/solr/collection1";
    private static final String INDEXING_URL = "http://localhost:8983/solr/collection1";

    private boolean keepCacheFiles = false;
    protected Log log = LogFactory.getLog(ProcessRSS.class);
    protected DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MMMM-yyyy:HH:ss:mm");
    private SocialIntellegentiaAPI siAPI = new SocialIntellegentiaAPI();

    private SpiderPersistence spiderPersistence = new SpiderPersistence();

    private SolrHelper solrHelper = new SolrHelper(QUERYING_URL,INDEXING_URL);
    private SolrIndexer solrIndexer = new SolrIndexer(QUERYING_URL,INDEXING_URL);

    private INamedEntityRecognizer spanishNer;
    private INamedEntityRecognizer englishNer;


    public ProcessRSS() {
        init(false);
    }

    public ProcessRSS(boolean keepCacheFiles) {
        init(keepCacheFiles);
    }

    private void init(boolean keepCacheFiles) {
        this.keepCacheFiles = keepCacheFiles;
        try {
            spanishNer = new FreeLingSpanishBioNerNer();
            englishNer = new FreeLingEnglishBioNerNer();
        } catch (ServiceUnavailableException e) {
            log.error("NATURAL LANGUAGE PROCESS EPIC FAIL !!!");
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error("NATURAL LANGUAGE PROCESS EPIC FAIL !!!");
            log.error(e.getMessage());
        }
    }

    public void processRSSfromWorkingDirectory(String workingFolder) throws Exception {

        DateTime dtBegin = new DateTime();
        log.debug("[ProcessRSS] --> Begin process injection in " + workingFolder + " in: " + fmt.print(dtBegin));

        List<Feed> feeds = getFeedsFromSeedsByPath(workingFolder);
        log.debug("[ProcessRSS] --> Catch process injection with: [" + feeds.size() + "] Feeds");

        for (Feed feed : feeds) {
            if (feed==null) continue;
            feed = spiderPersistence.deleteExistingFeedMessagesFromFeed(feed);
            if (!feed.getFeedMessages().isEmpty())
            {
                spiderPersistence.saveFeed(feed);
                loadFeedInServer(feed);
                indexFeedInSolr(feed);
            }
        }

        DateTime dtEnd = new DateTime();
        Period totalPeriod = new Period(dtBegin, dtEnd, PeriodType.time());
        String strTotalTime=  totalPeriod.getHours() + ":" + totalPeriod.getSeconds() + ":" + totalPeriod.getMillis();
        log.debug("[ProcessRSS] --> Finish process injection in " + workingFolder + " in: " + fmt.print(dtEnd) +  ". Time: " + strTotalTime + "\n");

    }

    protected void indexFeedInSolr(Feed feed) {

        for (FeedMessage feedMessage : feed.getFeedMessages())
        {

            solrHelper.setNerEngine(spanishNer);
            SolrInputDocument solrFeedMessage = solrHelper.createSolrDocumentFromFeedMessage(feedMessage);
            String corpus = getAllCorpusFromFeed(solrFeedMessage);
            solrFeedMessage = solrHelper.injectElementsFromNamedEntityExtraction(solrFeedMessage, corpus);

            solrIndexer.index(solrFeedMessage);
        }

    }

    private String getAllCorpusFromFeed(SolrInputDocument solrFeedMessage) {
        StringBuilder corpus = new StringBuilder();
        corpus.append( solrFeedMessage.getFieldValue("title").toString());
        corpus.append( "." );
        corpus.append(solrFeedMessage.getFieldValue("description").toString());
        return corpus.toString();
    }

    public List<Feed> getFeedsFromSeedsByPath(String path) throws Exception {
        List<String> fileList = FileHelper.getFileList(path, "");
        RSSFeedParser parser = new RSSFeedParser();
        List<Feed> feedList = new ArrayList<Feed>();

        for(String filePath : fileList)
        {
            String fileString = FileHelper.fileToString(filePath);
            Feed feed = parser.readFeed(fileString);
            feedList.add(feed);
            if (!keepCacheFiles) FileHelper.deleteFile(filePath);
        }

        return feedList;
    }

    public String loadFeedInServer(Feed feed) throws Exception {

        if (feed==null) {
            log.error("[ProcessRSS] --> Feed is null ");
            return "";
        }

        DateTime dtBegin = new DateTime();
        String title = feed.getTitle();
        log.debug("[ProcessRSS] --> Inject into server " + title + " in: " + fmt.print(dtBegin));


        String addFeed = siAPI.newsFeedService_addFeed(TOKEN, feed);

        log.debug("[ProcessRSS] --> Server response: [" + addFeed + "]");
        log.debug("[ProcessRSS] --> With : [" + feed.toJson() + "]");
        DateTime dtEnd = new DateTime();
        Period totalPeriod = new Period(dtBegin, dtEnd, PeriodType.time());
        String strTotalTime=  totalPeriod.getHours() + ":" + totalPeriod.getSeconds() + ":" + totalPeriod.getMillis();
        log.debug("[ProcessRSS] --> Finish injection "+ addFeed +" in: " + fmt.print(dtEnd) +  ". Time: " + strTotalTime + "\n");

        return addFeed;
    }


}
