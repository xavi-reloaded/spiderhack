package com.socialintellegentia.entryPoints;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.commonsHelpers.WgetHelper;
import com.androidxtrem.nlp.contents.StandardContentKyoto;
import com.androidxtrem.nlp.keywords.CustomProfileKeywords;
import com.androidxtrem.spider.si.SpiderSiRSS;
import com.androidxtrem.util.ExpectedTimeGenerator;
import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
import com.socialintellegentia.commonhelpers.mailer.MailSender;
import com.socialintellegentia.commonhelpers.rss.RSSHelper;
import com.socialintellegentia.processes.ProcessRSS;
import com.socialintellegentia.util.JsonHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jonny
 * Date: 12/07/13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class directProcess {


    private static final String PROCESS_FILE = "spider.pid";
    private static StringBuilder report;
    private static StringBuilder errorReport;
    private static StringBuilder badRequestReport;
    protected static Log log = LogFactory.getLog(directProcess.class);





    public static void main(String[] args) {
        args = new String[]{"/home/sidev/workspace/bin/20130909_Sources_Feeds_Json.txt"};
//        args = new String[]{"{\"source\":\"http://www.npr.org/rss/rss.php?id=1001\"}"};
        if (args.length < 1) {
            printUsage();
            return;
        }

        if (checkIfProcessIsRunning()) return;

        long tStart = System.currentTimeMillis();
        report = new StringBuilder();
        errorReport = new StringBuilder();
        badRequestReport = new StringBuilder();
        String sourceFile = args[0];

        CustomProfileKeywords customProfileKeywords = new CustomProfileKeywords("en");




        List<String> rssSources = null;

        report.append("ready to process... \n\n\n\n").append(new Date()).append("\n");

        try {
//            if (!sourceFile.contains("{\"source\":\"")) throw new Exception("is not a valid source");
            rssSources = getRssSources(sourceFile);
            report.append("files to process: ").append(rssSources.size()).append("\n\n");
        } catch (IOException e) {
            String errormsg = "Error openning file: [" + rssSources + "]\n[" + e.getMessage() + "]";
            System.out.println(errormsg);
            sendErrorMessage(e,errormsg);
            e.printStackTrace();
            return;
        } catch (JSONException e) {
            String errormsg = "Error in JSON parsing: [" + e.getMessage() + "]";
            System.out.println(errormsg);
            sendErrorMessage(e,errormsg);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            String errormsg = "Error in JSON parsing: [" + e.getMessage() + "]";
            System.out.println(errormsg);
            sendErrorMessage(e,errormsg);
            e.printStackTrace();
            return;
        }

        int cont = 0;
        int contError = 0;
        int contBadRequest = 0;
        int contFeedMessages = 0;
        int totalFiles = rssSources.size();
        for (String rssSource : rssSources) {

            try {
                long startProcess = System.currentTimeMillis();
                cont++;
                log.debug("BEGIN PROCES ("+cont+" of "+totalFiles+") ["+rssSource+"]");

                String rss = WgetHelper.get(rssSource);

                File tempFolder = FileHelper.createTempFolder("spider");
                String filePathName = tempFolder.getPath() + "/1.rss";
                FileHelper.stringToFile(rss, filePathName);

                int percentage = ( cont * 100) / totalFiles;
                report.append("BEGIN PROCESS ("+cont+" of "+totalFiles+")("+percentage+"%)\n[  "+rssSource+"  ]").append("\n");

                if (!RSSHelper.isXMLRSS(rss)&&!RSSHelper.isXMLFeed(rss)) {
                    contBadRequest++;
                    String errorMessage = "BAD REQUEST : [" + rssSource + "] is not a valid rss resource";
                    log.warn(errorMessage);
                    badRequestReport.append(rssSource).append("\n");
                    report.append("BAD REQUEST  [" + rssSource + "]");
                    report.append(" is XMLRSS? "+RSSHelper.isXMLRSS(rss));
                    report.append(" is XMLFeed? " + RSSHelper.isXMLFeed(rss));
                    report.append(" is empty? " + rss.trim().equals(""));
                    report.append("\n\n");
                    File file = new File(filePathName);
                    if (file.canWrite()) {
                        FileHelper.deleteFile(file);
                    }
                    continue;
                }

                ProcessRSS processRSS = new ProcessRSS(customProfileKeywords);
                int processMessages = processRSS.processRSSfromWorkingDirectory(tempFolder.getPath());

                report.append("END PROCESS FOR ["+processMessages+"] MESSAGES in ("+getElapsedSeconds(startProcess)+" seconds)\n\n");
                contFeedMessages = contFeedMessages + processMessages;

            } catch (IOException e) {
                contError = errorReport(contError, rssSource, e);
            } catch (InterruptedException e) {
                contError = errorReport(contError, rssSource, e);
            } catch (Exception e) {
                contError = errorReport(contError, rssSource, e);
            }

        }

        try {
            FileHelper.deleteExistentFile(new File(PROCESS_FILE));
        } catch (IOException e) {
            errorReport.insert(0,"Error deleting pid process file\n\n");
        }

        report.append("finish at ").append(new Date()).append("\n");

        System.out.println("End of Routine: [" + "]");
        if (!errorReport.toString().equals("")) {
            errorReport.append("\n\n DIO CANE DE DIO ! ! ! !\n "+contError+" ERRORE ! ! ! ! !");
            MailSender.sendErrorMessage(errorReport.toString(), "spider error report");
        }
        MailSender.sendErrorMessage(report.toString(), "spider report");
        if (!badRequestReport.toString().equals("")) {
            MailSender.sendErrorMessage(badRequestReport.toString(), "spider bad request report");
        }
        double elapsedSeconds = getElapsedSeconds(tStart);
        MailSender.sendErrorMessage("\n\n" +
                "datetime: " + new Date() +"\n"+
                "total sources: "+cont+"\n" +
                "total errors: "+contError+"\n" +
                "total bad request: "+contBadRequest+"\n" +
                "total feed messages (news): "+contFeedMessages+"\n" +
                "________________________________________\n" +
                "time total: "+ elapsedSeconds +"\n" +
                "time average per message: "+(elapsedSeconds/cont)+"\n"+
                "________________________________________\n"
                , "spider aggregated report");





        System.exit(0);

    }

    private static boolean checkIfProcessIsRunning() {
        String processId = "0000";
        try {
            processId= getProcessId();
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        }

        if (FileHelper.fileExists(PROCESS_FILE)) {
            System.out.println("The spider is currently running");
            String currentProcess = "0000";
            try {
                currentProcess = FileHelper.fileToString(PROCESS_FILE);
            } catch (IOException e) {

            }
            Runtime runtime = Runtime.getRuntime();
            NumberFormat format = NumberFormat.getInstance();

            StringBuilder sb = new StringBuilder();
            sb.append(new Date());
            sb.append("\n\n\n");

            long maxMemory = runtime.maxMemory();
            long allocatedMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();


            sb.append("\n\n\n");
            sb.append("\nThis spider thread runs with process ["+processId+"]");
            sb.append("\nbut the spider main thread working is ["+ currentProcess +"]");
            sb.append("\nso fortunately we do nothing... ");
            sb.append("\n__________________________________________\n");
            sb.append("free memory: " + format.format(freeMemory / 1024) + "\n");
            sb.append("allocated memory: " + format.format(allocatedMemory / 1024) + "\n");
            sb.append("max memory: " + format.format(maxMemory / 1024) + "\n");
            sb.append("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) + "\n");
            sb.append("\n\n\n\n\n\n\n");

            MailSender.sendErrorMessage(sb.toString(), "[spider ping] [" + currentProcess + "]");
            return true;
        } else {
            try {
                FileHelper.stringToFile(processId, PROCESS_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static double getElapsedSeconds(long startProcess) {
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - startProcess;
        return tDelta / 1000.0;
    }

    private static int errorReport(int contError, String rssSource, Exception e) {
        contError++;
        System.out.println("\n\n\n\n\n\n\nError when running spider: [" + e.getMessage() + "]\n\n");
        errorReport.append("error:" + e.toString() + "^date:" + new Date() + "^Error trace:[" + e.getMessage() + "]^File:" + rssSource + "^").append("\n");
        return contError;
    }

    private static void sendErrorMessage(Exception e, String errorMessage) {
        MailSender.sendErrorMessage(
                "error:\n" + e.toString() + "\n\ndate:\n" + new Date() + "\n\nError trace:\n" + e.getMessage() + "\n\n" +
                        "Error Message:\n"+errorMessage,
                "Error in spider [" + e.toString() + "]"
        );
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

    private static String getProcessId() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        java.lang.management.RuntimeMXBean runtime = java.lang.management.ManagementFactory.getRuntimeMXBean();
        java.lang.reflect.Field jvm = runtime.getClass().getDeclaredField("jvm");
        jvm.setAccessible(true);
        sun.management.VMManagement mgmt = (sun.management.VMManagement) jvm.get(runtime);
        java.lang.reflect.Method pid_method = mgmt.getClass().getDeclaredMethod("getProcessId");
        pid_method.setAccessible(true);

        int pid = (Integer) pid_method.invoke(mgmt);
        return ""+pid;
    }


}
