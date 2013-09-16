package com.socialintellegentia.entryPoints;

import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
import com.socialintellegentia.processes.ProcessRSS;

/**
 * Created with IntelliJ IDEA.
 * User: sidev
 * Date: 9/16/13
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessRSSThread extends Thread{

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
        } finally {
            System.out.println("DONE! thread: [" + getName() +"]");
        }
    }
}
