package com.socialintellegentia.entryPoints;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.google.common.collect.Lists;
import com.socialintellegentia.processes.ProcessRSS;
import com.socialintellegentia.util.JsonHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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
        String workingDirectory = (args.length == 2) ? args[1] : "/home/sidev/workspace/bin/sd_spider/spider/SpiderSiRSS";

        String filePath_ = "";
        String rss       = "";
        try {

//            List<String> fileList = FileHelper.getFileList(workingDirectory, "");
            List<String> fileList = getFileList(workingDirectory);
            int cont=0;
            int totalFiles = fileList.size();

            for (String filePath : fileList)
            {
                filePath_=filePath;

                cont++;
                log.debug("BEGIN PROCES ("+cont+" of "+totalFiles+") ["+filePath+"]");

                if (!FileHelper.fileExists(filePath)) continue;

                rss = FileHelper.fileToString(filePath);

//                if (!RSSHelper.isXMLRSS(rss)&&!RSSHelper.isXMLFeed(rss)) {
//                    log.warn("BAD REQUEST : ["+filePath+"] is not a valid rss resource, must be a FrontEnd RSS page");
//                    File file = new File(filePath);
//                    if (file.canWrite()) {
//                        FileHelper.deleteFile(file);
//                    }
//                    continue;
//                }

                ProcessRSS processRSS = new ProcessRSS();
//                processRSS.processRSSfromWorkingDirectory(filePath);

                log.debug("BEGIN PROCES (" + cont + " of " + totalFiles + ") [" + filePath + "]");
            }


        } catch (Exception e) {
            log.error("MIERDER HAS HAPPENED INTO PROCESS ROUTINE (" + e.toString() + "]");

            e.printStackTrace();
            System.exit(0);
        }
    }

    private static List<String> getFileList(String workingDirectory) throws IOException, JSONException {
        boolean isDirectory = new File(workingDirectory).isDirectory();

        if (isDirectory) return FileHelper.getFileList(workingDirectory, "");

        List<String> fileList = Lists.newArrayList();
        fileList.add( workingDirectory );

        return fileList;
    }


}
