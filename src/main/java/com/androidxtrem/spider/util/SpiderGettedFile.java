package com.androidxtrem.spider.util;

import com.androidxtrem.spider.SpiderConfig;
import com.androidxtrem.spider.domain.DataFax;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 7/30/12
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpiderGettedFile implements SpiderConfig {

    private int count = 0;
    private int total = 0;
    private FileWriter writer;
    private static final CharSequence CSV_SEPARATOR = "^";


    public DataFax getDataFromGettedFile(File sample) throws IOException {
        DataFax dataFax = new DataFax();

        FileInputStream fstream = new FileInputStream(sample);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;

        while ((strLine = br.readLine()) != null)   {
           if (strLine.startsWith("url")) dataFax.setUrl(strLine.replaceAll("url\\[(.+?)\\]","$1"));
           if (strLine.startsWith("fax")) dataFax.setFax(parseFaxNumber(strLine));
        }

        fstream.close();
        in.close();

        return dataFax;
    }

    public String parseFaxNumber(String strLine) {
        strLine = strLine.replace(" ","");
        strLine = strLine.replace("+","");
        strLine = strLine.replace("(","");
        strLine = strLine.replace(")","");
        strLine = strLine.replace(".","");
        strLine = strLine.replace("-","");
        strLine = strLine.replace("/","");
        strLine = strLine.replaceAll("fax\\[(.+?)\\]", "$1");
        strLine = strLine.replaceAll("[^0-9]+([0-9]+)[^0-9]+", "$1");
        //strLine = strLine.replaceAll("34([0-9]+)","$1");
        strLine = strLine.trim();
        if (!strLine.startsWith("3")) {
            strLine = "34"+strLine;
        }
        strLine = "+"+strLine;

        if (strLine.length()>12) {
            strLine = strLine.substring(0,12);
        }

        return strLine;
    }

    public int getDataFromGettedFolder(File folder) throws IOException {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                getDataFromGettedFolder(fileEntry);
            } else {
                DataFax dataFax = getDataFromGettedFile(fileEntry);
                count++;
                if (writer!=null) {
                    writeToCsvFile(writer, fileEntry, dataFax,folder.getName());
                }
//                System.out.println("name: [(" + count + ") => " + fileEntry.getPath() + "] \n[" +dataFax.getUrl()+"] :: "+ dataFax.getFax() );
            }
            total++;
        }
        return count;
    }



    public int createCSVfromGettedFolder(File folder) throws IOException {
        writer = new FileWriter(WORKING_FOLDER + "/faxosCSV.csv");

        writer.append("ID").append(CSV_SEPARATOR).append("FOLDERNAME").append(CSV_SEPARATOR).append("PATH").append(CSV_SEPARATOR).append("URL").append(CSV_SEPARATOR).append("FAX").append('\n');

        getDataFromGettedFolder(folder);

        writer.flush();
        writer.close();

        return count;
    }

    private void writeToCsvFile(FileWriter writer, File fileEntry, DataFax dataFax, String folderName) throws IOException {
        writer.append(String.valueOf(count));
        writer.append(CSV_SEPARATOR).append(folderName);
        writer.append(CSV_SEPARATOR).append(fileEntry.getPath());
        writer.append(CSV_SEPARATOR).append(dataFax.getUrl());
        writer.append(CSV_SEPARATOR).append(dataFax.getFax());
        writer.append('\n');
    }


}
