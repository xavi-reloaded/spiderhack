package com.socialintellegentia.entryPoints;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
import com.socialintellegentia.commonhelpers.rss.RSSHelper;
import com.socialintellegentia.processes.ProcessRSS;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sidev
 * Date: 9/16/13
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */


public class process {

    public static void main(String[] args) {
        SpiderPersistence persistence = new SpiderPersistence();
        String workingDirectory = "/home/sidev/workspace/bin/sd_spider/spider/SpiderSiRSS";

        try {

            List<String> fileList = FileHelper.getFileList(workingDirectory, "");
            int cont=0;

            for (String filePath : fileList)
            {
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nBEGIN PROCES ("+cont+") ["+filePath+"]\n\n");

                String rss = FileHelper.fileToString(filePath);
                if (!RSSHelper.isXMLRSS(rss)) {
                    System.out.println("BAD REQUEST (is not a valid rss resource) \n\n");
                    continue;
                }

                ProcessRSS processRSS = new ProcessRSS(persistence);
                processRSS.setKeepCacheFiles(true);
                processRSS.processRSSfromWorkingDirectory(filePath);
                processRSS.flush();

                cont++;
                System.out.println("\n\nEND PROCES ("+cont+") ["+filePath+"]\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            }


        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
