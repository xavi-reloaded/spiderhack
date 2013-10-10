package com.socialintellegentia.entryPoints;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.si.SpiderSiRSS;
import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
import com.socialintellegentia.processes.ProcessRSS;
import com.socialintellegentia.solr.SolrAdapter;
import com.socialintellegentia.solr.SolrHelper;
import com.socialintellegentia.solr.SolrIndexer;
import com.socialintellegentia.util.JsonHelper;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: jonny
 * Date: 12/07/13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class spider {


    private static String workingFolder;



    public static void main(String[] args) {
//        args = new String[]{"/home/sidev/workspace/bin/20130909_Sources_Feeds_Json.txt"};
//        args = new String[]{"{\"source\":\"http://www.npr.org/rss/rss.php?id=1001\"}"};
        if (args.length < 1) {
            printUsage();
            return;
        }
        if (args[0].equals("-X")) {
            process.main(args);
            return;
        }


        String sourceFile = args[0];
        SpiderPersistence spiderPersistence = new SpiderPersistence();


        List<String> rssSources = null;
        try {
//            if (!sourceFile.contains("{\"source\":\"")) throw new Exception("is not a valid source");
            rssSources = getRssSources(sourceFile);
        } catch (IOException e) {
            System.out.println("Error openning file: [" + rssSources + "]\n[" + e.getMessage() + "]");
            e.printStackTrace();
            return;
        } catch (JSONException e) {
            System.out.println("Error in JSON parsing: [" + e.getMessage() + "]");
            e.printStackTrace();
            return;
        } catch (Exception e) {
            System.out.println("Error in JSON parsing: [" + e.getMessage() + "]");
            e.printStackTrace();
            return;
        }


        SpiderSiRSS runner = null;

        try {
            runner = new SpiderSiRSS(spiderPersistence);
        } catch (IOException e) {
        }

        for (String rssSource : rssSources) {

            try {

//                if (spiderPersistence.isUrlInBlackList(rssSource)) {
//                    System.out.println("\n" +
//                            "\n____________________________________________________________________" +
//                            "\n B L A C K L I S T E D  ==>  " + rssSource +
//                            "\n____________________________________________________________________" +
//                            "\n");
//                    continue;
//                }

                workingFolder = new java.io.File(".").getCanonicalPath() + File.separator + runner.getWorkingFolder();
                System.out.println("\n" +
                        "\n____________________________________________________________________" +
                        "\n R E A D Y   T O   P R O C E S S    ==>  " + rssSource +
                        "\n____________________________________________________________________" +
                        "\n");

                runner.getNewsFromRSSserver(rssSource);


            } catch (IOException e) {
                System.out.println("\n\n\n\n\n\n\nError when running spider: [" + e.getMessage() + "]\n\n");
            } catch (InterruptedException e) {
                System.out.println("\n\n\n\n\n\n\nError when running spider: [" + e.getMessage() + "]\n\n");
            }



        }

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
                " -t                           publish to test server\n");
    }


}
