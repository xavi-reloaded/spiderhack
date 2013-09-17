package com.socialintellegentia.entryPoints;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
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
        ProcessRSS processRSS = new ProcessRSS(persistence);
        String workingDirectory = "/home/sidev/workspace/bin/sd_spider/spider/SpiderSiRSS";
        try {
            processRSS.setKeepCacheFiles(true);

            List<String> fileList = FileHelper.getFileList(workingDirectory, "");

            processRSS.processRSSfromWorkingDirectory(workingDirectory);

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
