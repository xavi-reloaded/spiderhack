package com.socialintellegentia.entryPoints;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.si.SpiderSiRSS;
import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
import com.socialintellegentia.processes.ProcessRSS;
import com.socialintellegentia.util.JsonHelper;
import org.json.JSONException;

import java.io.IOException;
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
        SpiderPersistence spiderPersistence = new SpiderPersistence();

        List<String> rssSources = null;
        try {
            rssSources = getRssSources(sourceFile);
        } catch (IOException e) {
            System.out.println("Error openning file: ["+rssSources+"]\n["+e.getMessage()+"]");
        } catch (JSONException e) {
            System.out.println("Error in JSON parsing: [" + e.getMessage() + "]");
        }

        SpiderSiRSS runner = null;

        try {
            runner = new SpiderSiRSS();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        for (String rssSource : rssSources) {
            if (spiderPersistence.isUrlInBlackList(rssSource)) {
                System.out.println("\n" +
                        "\n____________________________________________________________________" +
                        "\n B L A C K L I S T E D  ==>  " + rssSource +
                        "\n____________________________________________________________________" +
                        "\n");
                continue;
            }

            try {
                runner.getNewsFromRSSserver(rssSource);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }


//            runner.stop();

//            try {
//                String workingFolder = runner.getWorkingFolder();
//                ProcessRSS processRSS = new ProcessRSS();
//                processRSS.processRSSfromWorkingDirectory(workingFolder);
//            } catch (Exception e) {
//                System.out.println("Error when running spider: ["+e.getMessage()+"]");
//            }
        }
    }



    private static List<String> getRssSources(String sourceFile) throws IOException, JSONException {
        boolean fileExists = FileHelper.fileExists(sourceFile);
        String rssSourcesJson = (fileExists) ? FileHelper.fileToString(sourceFile) : "[{\"source\":\""+sourceFile+"\"}]";
        return JsonHelper.getRssSourcesFromJson(rssSourcesJson);
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
