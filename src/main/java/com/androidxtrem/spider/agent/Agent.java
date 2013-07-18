package com.androidxtrem.spider.agent;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.HttpDownloader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Agent extends HttpDownloader implements Runnable
{


    protected Log log = LogFactory.getLog(Agent.class);
    protected DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MMMM-yyyy:HH:ss:mm");

	private String m_workingFolder = "";
	private String m_seed = "";
	private String m_url = "";
	private Thread m_thread = null;
	private List<String> m_newLinks = new ArrayList<String>();

	public Agent(String workingFolder, String cacheFolder, long minutesInCache) throws IOException, InterruptedException
	{
		super(cacheFolder, minutesInCache, 10000, 60000);
		m_workingFolder = workingFolder;
	}

	public void start(String seed, String url) throws InterruptedException
	{
		if(m_thread != null && m_thread.isAlive())
		{
			throw new IllegalThreadStateException();
		}

		if(url != null && url.length() > 0)
		{
			m_seed = seed;
			m_url = url;
			m_thread = new Thread(this);
			m_thread.start();
		}
	}

	public String getSeed()
	{
		return m_seed;
	}

	public String getUrl()
	{
		return m_url;
	}

	public String getWorkingFolder()
	{
		return m_workingFolder;
	}

	public boolean isAlive()
	{
		return (m_thread != null) ? m_thread.isAlive() : false;
	}

	public List<String> getNewLinks()
	{
		return m_newLinks;
	}

	public void addNewLinks(List<String> newLinks)
	{
		m_newLinks.addAll(newLinks);
	}

	public void clearNewLinks()
	{
		m_newLinks.clear();
	}
	
	public void saveResult(StringBuffer result, String name) throws IOException
	{
		String outputFile = getWorkingFolder() + System.getProperty("file.separator") + name;
		if(!FileHelper.fileExists(outputFile))
		{
			FileHelper.stringToFile(result.toString(), outputFile);
			System.out.println("New result saved [" + outputFile + "] from [" + m_seed + "]");
		}
		else
		{
			//System.out.println("Repeated article [" + outputFile + "] from [" + m_seed + "]");
		}
	}

    public void saveResult(String text, String name) throws IOException {
        saveResult(new StringBuffer(text),name);
    }


    public void reportError(String name, String url, String html) throws IOException, InterruptedException
	{
		String folder = m_workingFolder + System.getProperty("file.separator") + "errors" + System.getProperty("file.separator") + name;
		FileHelper.createFolder(folder);
		String file = folder + System.getProperty("file.separator") + m_url.replaceAll("[=\\\\/:\\*\"\\?<>\\|]", "") + ".html";
		if(!FileHelper.fileExists(file))
			FileHelper.stringToFile(url + "\n\n" + html, file);
	}

	abstract public void run();

}
