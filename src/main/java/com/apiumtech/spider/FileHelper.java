package com.apiumtech.spider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class FileHelper
{
	static public void stringToFile(String str, String filePath) throws IOException
	{
		stringToFile(str, filePath, false, null);
	}
	
	static public void stringToFile(String str, String filePath, boolean append) throws IOException
	{
		stringToFile(str, filePath, append, null);
	}
	
	static public void stringToFile(String str, String filePath, boolean append, String encoding /*"ISO-8859-1"*/) throws IOException
	{
		BufferedWriter writer = null;
		if(encoding != null) writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, append), encoding));
		else writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, append)));
		writer.write(str);
		writer.flush();
		writer.close();
	}

	static public String fileToString(String filePath) throws IOException
	{
		return fileToString(filePath, null);
	}
	
	static public String fileToString(String filePath, String encoding /*"ISO-8859-1"*/) throws IOException
	{
		StringBuilder text = new StringBuilder();
		BufferedReader rd = null;
		if(encoding != null) rd = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
		else rd = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
		String line;

		while((line = rd.readLine()) != null)
		{
			text.append(line);
		}
		rd.close();

		return text.toString();
	}

	static public boolean fileExists(String filePath)
	{
		File cvsFile = new File(filePath);
		return cvsFile.exists();
	}

	static public void createFolder(String folder) throws IOException, InterruptedException
	{
		boolean r = false;
		File cvsFile = new File(folder + File.separator);
		if((r = cvsFile.exists()) == false)
		{
			if((r = cvsFile.mkdirs()) == false)
			{
				r = cvsFile.mkdirs();
			}
		}
		if(!r) throw new IOException("Unable to create folder [" + folder + "]");
	}

	static public ArrayList<String> getFileList(String path, String filter)
	{
		ArrayList<String> files = new ArrayList<String>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		if(listOfFiles != null)
		{
			for(int i = 0; i < listOfFiles.length; i++)
			{
				if(listOfFiles[i].isFile())
				{
					if(listOfFiles[i].getName().endsWith(filter))
					{
						files.add(listOfFiles[i].getAbsolutePath());
					}
				}
				else if(listOfFiles[i].isDirectory())
				{
					files.addAll(getFileList(listOfFiles[i].getPath(), filter));
				}
			}
		}

		return files;
	}

	static public String getGzipText(String filePath) throws IOException
	{
		String r = "";
		FileInputStream fin = new FileInputStream(filePath);
		GZIPInputStream gzis = new GZIPInputStream(fin);
		InputStreamReader reader = new InputStreamReader(gzis);
		BufferedReader is = new BufferedReader(reader);

		String line;
		while((line = is.readLine()) != null)
		{
			r += line + " ";
		}

		return r;
	}
	
	static public void deleteFile(String filePath) throws Exception
	{
		File f = new File(filePath);
	    if(f.exists())
	    {
	    	if(!f.canWrite()) throw new Exception("Unable to access file: " + filePath);
	    	if(f.isDirectory()) throw new IllegalArgumentException("[" + filePath + "] is not a file");
	    	f.delete();
	    }
	}
}
