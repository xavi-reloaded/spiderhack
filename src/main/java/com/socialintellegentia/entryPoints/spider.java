package com.socialintellegentia.entryPoints;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.apiumtech.spider.si.SpiderSiRSS;
import com.socialintellegentia.commonhelpers.rss.Feed;
import com.socialintellegentia.processes.ProcessRSS;
import com.socialintellegentia.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jonny
 * Date: 12/07/13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class spider {


    public static void main(String[] args)
    {

        if (args.length<1) {
            printUsage();
            return;
        }
        String sourceFile = args[0];

        try
        {

            String rssSourcesJson = FileHelper.fileToString(sourceFile);

            ProcessRSS processRSS = new ProcessRSS();
            List<String> rssSources = JsonHelper.getRssSourcesFromJson(rssSourcesJson);

            for (String rssSource : rssSources) {
                SpiderSiRSS runner = new SpiderSiRSS();
                runner.getNewsFromRSSserver(rssSource);
                runner.stop();
                String workingFolder = runner.getWorkingFolder();
                processRSS.processRSSfromWorkingDirectory(workingFolder);
            }

        }
        catch (Exception e){
            System.out.println("Error when running spider: ["+e.getMessage()+"]");
            e.printStackTrace();

        }
    }

    private static void printUsage() {
        System.out.println("Usage:\n" +
                " java -jar spider.jar [options] start a spider job\n" +
                "\n" +
                "Options:\n" +
                " -k                           keep cache files\n" +
                " -w[working directory]        change working directory\n" +
                " -c                           config file\n" +
                " -t                           publish to test server\n");
    }


}
