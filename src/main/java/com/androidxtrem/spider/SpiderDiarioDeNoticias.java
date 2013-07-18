package com.androidxtrem.spider;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.agent.Agent;
import com.androidxtrem.spider.agent.AgentDiarioDeNoticias;
import com.androidxtrem.spider.agent.AgentsManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SpiderDiarioDeNoticias extends AgentsManager
{
	void test() throws IOException, InterruptedException 
	{
		String jobName = "diario_de_noticias";
		String workingFolder = "/home/xavi/.gvfs/processes/" + jobName;
		String cacheFolder = "/home/xavi/.gvfs/processes/spidercache";
		int maxLinksPerSeed = 3000;
		int maxSpiderThreads = 25;
		int maxProxyThreads = 5;
		int milisecondsBetweenQueries = 1000;

		FileHelper.createFolder(workingFolder);
		FileHelper.createFolder(cacheFolder);
		AnonymousProxyManager proxyManager = new AnonymousProxyManager(cacheFolder, maxProxyThreads);
		List<Agent> spiderList = new ArrayList<Agent>();
		for(int i = 0; i < maxSpiderThreads; i++)
			spiderList.add(new AgentDiarioDeNoticias(workingFolder, cacheFolder, Long.MAX_VALUE));
		startAgentsManager(milisecondsBetweenQueries, proxyManager, spiderList);

		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = Calendar.getInstance();
		String lastDate = dateFormatter.format(calendar.getTime());
		calendar.set(2004, 1, 1);
		while(true)
		{
			String formatedDate = dateFormatter.format(calendar.getTime());
			addNewSeed(new Seed("http://www.dn.pt/pesquisa/default.aspx?data=" + formatedDate, maxLinksPerSeed));
			calendar.add(Calendar.DATE, 1);
			if(formatedDate.compareTo(lastDate) == 0) break;
		}

		// addNewSeed(new Seed("http://www.dn.pt/pesquisa/default.aspx?data=20-02-2010", maxLinksPerSeed));

		while(getSeedCount() > 0)
		{
			Thread.sleep(50);
		}

		stopAgentsManager();
		proxyManager.stop();
	}
}
