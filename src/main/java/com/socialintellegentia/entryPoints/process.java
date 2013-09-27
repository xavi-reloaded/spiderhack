package com.socialintellegentia.entryPoints;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
import com.socialintellegentia.commonhelpers.rss.RSSHelper;
import com.socialintellegentia.processes.ProcessRSS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sidev
 * Date: 9/16/13
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */


public class process {

    protected static Log log = LogFactory.getLog(process.class);


    public static void main(String[] args) {
        SpiderPersistence persistence = new SpiderPersistence();
        String workingDirectory = "/home/sidev/workspace/bin/sd_spider/spider/SpiderSiRSS";

        try {

            List<String> fileList = FileHelper.getFileList(workingDirectory, "");
            int cont=0;
            int totalFiles = fileList.size();

            for (String filePath : fileList)
            {
                cont++;
                log.debug("BEGIN PROCES ("+cont+" of "+totalFiles+") ["+filePath+"]");

                if (!FileHelper.fileExists(filePath)) continue;

                String rss = FileHelper.fileToString(filePath);
                if (!RSSHelper.isXMLRSS(rss)) {
                    log.warn("BAD REQUEST : ["+filePath+"] is not a valid rss resource, must be a FrontEnd RSS page");
                    File file = new File(filePath);
                    if (file.canWrite()) {
                        FileHelper.deleteFile(file);
                    }
                    continue;
                }

                ProcessRSS processRSS = new ProcessRSS(persistence);
                processRSS.processRSSfromWorkingDirectory(filePath);

                log.debug("BEGIN PROCES (" + cont + " of " + totalFiles + ") [" + filePath + "]");
            }


        } catch (Exception e) {
            log.error("MIERDER HAS HAPPENED INTO PROCESS ROUTINE (" + e.toString() + "]");
            e.printStackTrace();
            System.exit(0);
        }
    }

}
