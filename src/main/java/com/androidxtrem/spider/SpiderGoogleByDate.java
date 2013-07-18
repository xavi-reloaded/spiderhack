package com.androidxtrem.spider;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.agent.Agent;
import com.androidxtrem.spider.agent.AgentWordContext;
import com.androidxtrem.spider.agent.AgentsManager;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SpiderGoogleByDate extends AgentsManager
{	
	void test() throws IOException, InterruptedException
	{
		{
			// String workingfolder = "\\\\ficheros\\65-XTYLE\\processes\\wordcontext";
			// String cachefolder = "\\\\ficheros\\65-XTYLE\\processes\\spidercache";
			// String googleSearch =
			// "\"Read%20full%20review\"+Advantages+Reviews+sea+site:ciao.co.uk";
			// int maxSimultaneousSeeds = 15;
			// int maxLinksPerSeed = 3000;
			// int maxSpiderThreads = 50;
			// int maxProxyThreads = 20;
			// int milisecondsBetweenQueries = 10000;

			int i = 0;
			String jobName = "wordcontext_caramel";
			String workingFolder = "/home/xavi/.gvfs/processes/" + jobName;
			String cacheFolder = "/home/xavi/.gvfs/processes/spidercache";
			String googleSearch = "\"caramel\"";
			int maxSimultaneousSeeds = 15;
			int maxLinksPerSeed = 3000;
			int maxSpiderThreads = 2;
			int maxProxyThreads = 4;
			int milisecondsBetweenQueries = 1000;

			FileHelper.createFolder(workingFolder);
			FileHelper.createFolder(cacheFolder);
			AnonymousProxyManager proxyManager = new AnonymousProxyManager(cacheFolder, maxProxyThreads);
			List<Agent> spiderList = new ArrayList<Agent>();
			for(i = 0; i < maxSpiderThreads; i++) spiderList.add(new AgentWordContext("caramel", workingFolder, cacheFolder, Long.MAX_VALUE));
			startAgentsManager(milisecondsBetweenQueries, proxyManager, spiderList);

			long googlePage = 0;
			long googleDate = 0;
			while (true)
			{
				// get next seed if
				if(googlePage < 1000)
				{
					if(getSeedCount() < maxSimultaneousSeeds)
					{
						for(i = 0; i < spiderList.size(); i++)
							if(!spiderList.get(i).isAlive())
								break;
						if(i < spiderList.size())
						{
							Format formatter = new SimpleDateFormat("MM/dd/yyyy");
							Date date = Calendar.getInstance().getTime();
							date.setTime(date.getTime() - (long) (1000L * 60L * 60L * 24L * googleDate));
							String formattedDate = formatter.format(date);
							String newSeed = "http://www.google.com/search?q=" + googleSearch + "&start=" + googlePage + "&num=100" + "&tbs=cdr:1,cd_min:" + formattedDate + ",cd_max:" + formattedDate;
							googlePage += 100;
							addNewSeed(new Seed(newSeed, maxLinksPerSeed));
							System.out.println("New seed [" + newSeed + "]");
						}
					}
				}
				else if(googleDate < 365) // one year
				{
					googleDate++;
					googlePage = 0;
				}
				else
				{
					// quit ?
					for(i = 0; i < spiderList.size(); i++)
						if(spiderList.get(i).isAlive())
							break;
					if(i == spiderList.size())
						break;
				}

				Thread.sleep(50);
			}
		}
	}

}
