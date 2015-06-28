package com.socialintellegentia.entryPoints;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.SpiderWasshotFeed;
import com.androidxtrem.spider.si.SpiderSiRSS;
import com.rometools.rome.io.FeedException;
import com.socialintellegentia.util.JsonHelper;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jonny
 * Date: 12/07/13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class WasshotSpider {


    private static String workingFolder;
    private static StringBuilder report;
    private static StringBuilder errorReport;



    public static void main(String[] args) throws FeedException {
//        args = new String[]{"/home/sidev/workspace/bin/20130909_Sources_Feeds_Json.txt"};
        args = new String[]{"{\"source\":\"http://www.techlearning.com/rss\"}"};
        if (args.length < 1) {
            printUsage();
            return;
        }
        if (args[0].equals("-X")) {
            process.main(args);
            return;
        }

        if (args[0].equals("-DP")) {
            directProcess.main(args);
            return;
        }

        report = new StringBuilder();
        errorReport = new StringBuilder();
        String sourceFile = args[0];



        List<String> rssSources = null;

        report.append("ready to process... \n").append(new Date());

        try {
//            if (!sourceFile.contains("{\"source\":\"")) throw new Exception("is not a valid source");
            rssSources = getRssSources(sourceFile);
            report.append("files to process: ").append(rssSources.size());
        } catch (IOException e) {
            String errormsg = "Error openning file: [" + rssSources + "]\n[" + e.getMessage() + "]";
            System.out.println(errormsg);
            e.printStackTrace();
            return;
        } catch (JSONException e) {
            String errormsg = "Error in JSON parsing: [" + e.getMessage() + "]";
            System.out.println(errormsg);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            String errormsg = "Error in JSON parsing: [" + e.getMessage() + "]";
            System.out.println(errormsg);
            e.printStackTrace();
            return;
        }


        SpiderWasshotFeed runner = null;

        try {
            runner = new SpiderWasshotFeed();
        } catch (IOException e) {
            System.out.println("SpiderSiRSS can not connect");
            e.printStackTrace();
        }

        for (String rssSource : rssSources) {

            try {

                runner.getNewsFromRSSserver(rssSource);
                report.append(rssSource).append("\n");

            } catch (IOException e) {
            } catch (InterruptedException e) {
            }


        }

        report.append("finish at ").append(new Date()).append("\n");

        System.out.println("End of Routine: [" + "]");
        System.exit(0);

    }


    private static List<String> getRssSources(String sourceFile) throws IOException, JSONException {
        boolean fileExists = FileHelper.fileExists(sourceFile);
        String rssSourcesJson = (fileExists) ? FileHelper.fileToString(sourceFile) : "["+sourceFile+"]";
        return JsonHelper.getRssSourcesFromJson(rssSourcesJson);
    }

    private static void printUsage() {
        System.out.println("Usage:\n" +
                " java -jar spider.jar [options] start a spider job\n" +
                "\n" +
                "Options:\n" +
                " -k                           keep cache files\n" +
                " -w[working directory]        change working directory\n" +
                " -X                           launch rss injection\n" +
                " -DP                          direct process\n");
    }


}
