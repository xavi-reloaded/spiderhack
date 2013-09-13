package com.socialintellegentia.entryPoints;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.si.SpiderSiRSS;
import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
import com.socialintellegentia.processes.ProcessRSS;
import com.socialintellegentia.util.JsonHelper;
import org.json.JSONException;

import java.io.File;
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


    private static String workingFolder;

    public static void main(String[] args)
    {
        args = new String[]{"/home/sidev/workspace/bin/20130909_Sources_Feeds_Json.txt"};
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
            workingFolder = new java.io.File( "." ).getCanonicalPath() + File.separator + runner.getWorkingFolder();
        } catch (IOException e) {
            System.out.println("Error when running spider: ["+e.getMessage()+"]");
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
                System.out.println("\n" +
                        "\n____________________________________________________________________" +
                        "\n R E A D Y   T O   P R O C E S S    ==>  " + rssSource +
                        "\n____________________________________________________________________" +
                        "\n");
                runner.getNewsFromRSSserver(rssSource);
            } catch (IOException e) {
                System.out.println("Error when running spider: ["+e.getMessage()+"]");
            } catch (InterruptedException e) {
                System.out.println("Error when running spider: ["+e.getMessage()+"]");
            }

            new ProcessRSSThread(rssSource,workingFolder,spiderPersistence).start();

        }
        runner.stop();
        System.out.println("End of Routine: [" + "]");
    }


    static class ProcessRSSThread extends Thread {

        private final String workingFolder;
        ProcessRSS processRSS;

        public ProcessRSSThread(String rssSource, String workingFolder, SpiderPersistence spiderPersistence) {
            super(rssSource);
            processRSS = new ProcessRSS(spiderPersistence);
            this.workingFolder = workingFolder;
        }

        public void run() {
            try {
                processRSS.processRSSfromWorkingDirectory(this.workingFolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("DONE! thread: [" + getName() +"]");
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
