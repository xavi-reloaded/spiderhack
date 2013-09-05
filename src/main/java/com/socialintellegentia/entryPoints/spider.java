package com.socialintellegentia.entryPoints;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.si.SpiderSiRSS;
import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
import com.socialintellegentia.processes.ProcessRSS;
import com.socialintellegentia.util.JsonHelper;

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
        String workingFolder = (args.length>1) ? args[1].substring(13) : "";

        try
        {
            boolean fileExists = FileHelper.fileExists(sourceFile);
            String rssSourcesJson = (fileExists) ? FileHelper.fileToString(sourceFile) : "[{\"source\":\""+sourceFile+"\"}]";

            ProcessRSS processRSS = new ProcessRSS();
            if (!workingFolder.equals("")) {
                processRSS.processRSSfromWorkingDirectory(workingFolder);
                return;
            }

            List<String> rssSources = JsonHelper.getRssSourcesFromJson(rssSourcesJson);


            for (String rssSource : rssSources) {
                if (spiderPersistence.isUrlInBlackList(rssSource)) {
                    System.out.println("\n" +
                            "\n____________________________________________________________________" +
                            "\n B L A C K L I S T E D  ==>  " + rssSource +
                            "\n____________________________________________________________________" +
                            "\n");
                    continue;
                }

                    SpiderSiRSS runner = new SpiderSiRSS();
                    runner.getNewsFromRSSserver(rssSource);
                    runner.stop();
                    workingFolder = runner.getWorkingFolder();
                    processRSS.processRSSfromWorkingDirectory(workingFolder);
            }
        }
        catch (Exception e){
            System.out.println("Error when running spider: ["+e.getMessage()+"]");
//            e.printStackTrace();
            main(new String[]{sourceFile ,"onlyProcess="+workingFolder });
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
