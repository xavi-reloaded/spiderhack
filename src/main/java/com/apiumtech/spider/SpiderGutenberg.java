package com.apiumtech.spider;

import com.apiumtech.spider.agent.Agent;
import com.apiumtech.spider.agent.AgentGutenbergEpub;
import com.apiumtech.spider.agent.AgentsManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpiderGutenberg extends AgentsManager implements SpiderConfig
{   String cacheFolder = WORKING_FOLDER + "/guttemberg";
	private AnonymousProxyManager proxyManager = null;
	int maxProxyThreads = 5;
	
	public SpiderGutenberg() throws IOException
	{
		proxyManager = new AnonymousProxyManager(cacheFolder, maxProxyThreads);
	}
	
	public void stop()
	{
		proxyManager.stop();
	}
	
	private void processPage(String language, String pageContents, int maxBooks)
	{
		//<h2><a href="/ebooks/14269">Aan de Zuidpool<br>De Aarde en haar Volken, 1913</a> (Dutch)</h2>
		//<h2><a href="/ebooks/15809">A Apple Pie</a> (English)</h2>
		List<List<String>> books = RegExpHelper.getMatches(pageContents, "\\\"(/ebooks/[0-9]+)\\\"[^\\(]+\\(([a-zA-Z]+)\\)", false);
		for(int j = 0; j < books.size(); j++)
		{
			List<String> book = books.get(j);
			if(book.get(2).toLowerCase().compareTo(language) == 0)
			{
				if(maxBooks > -1) if(getSeedCount() > maxBooks) break;
				if(addNewSeed(new Seed("http://www.gutenberg.org" + book.get(1), 1000)))
				{
					System.out.println(getSeedCount() + " - " + book.get(1) + " - " + book.get(2));
				}
			}				
		}
	}

	void getLanguageBooks(String language, String languageShort) throws IOException, InterruptedException
	{
		getLanguageBooks(language, languageShort, -1);
	}

	void getLanguageBooks(String language, String languageShort, int maxBooks) throws IOException, InterruptedException
	{
		//String language = "english";
		//String languageShort = "en";

		String jobName = "/gutenberg_epub_" + language;
		String workingFolder = WORKING_FOLDER + jobName;
		int maxSpiderThreads = 10;
		int milisecondsBetweenQueries = 5000;

		FileHelper.createFolder(workingFolder);
		FileHelper.createFolder(cacheFolder);
		//AnonymousProxyManager proxyManager = new AnonymousProxyManager(cachefolder, maxProxyThreads);
		List<Agent> spiderList = new ArrayList<Agent>();
		for(int i = 0; i < maxSpiderThreads; i++)
			spiderList.add(new AgentGutenbergEpub(workingFolder, cacheFolder, Long.MAX_VALUE));

		HttpDownloader downloader = new HttpDownloader(cacheFolder, 10 * 24 * 60, 10000, 60000);

		String url = "http://www.gutenberg.org/browse/languages/" + languageShort;
		String pageContents = downloader.getRequest(url).toString();
		processPage(language, pageContents, maxBooks);

		String abc = "abcdefghijklmnopqrstuvwxyz";
		for(int i = 0; i < abc.length(); i++)
		{
			url = "http://www.gutenberg.org/browse/titles/" + Character.toString(abc.charAt(i));
			pageContents = downloader.getRequest(url).toString();
			processPage(language, pageContents, maxBooks);
		}

		startAgentsManager(milisecondsBetweenQueries, proxyManager, spiderList);

		while(getSeedCount() > 0) Thread.sleep(50);

		stopAgentsManager();
		//proxyManager.stop();
	}
}
