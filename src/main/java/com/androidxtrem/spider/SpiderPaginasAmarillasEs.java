package com.androidxtrem.spider;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.core.*;
import com.androidxtrem.spider.agent.RobotPaginasAmarillasEs;
import com.androidxtrem.spider.core.proxy.AnonymousProxyManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpiderPaginasAmarillasEs extends RobotExerciser implements SpiderConfig
{
    //	String cachefolder = "/home/fjhidalgo/.gvfs/processes/spidercache_paginasamarillases";
	String cacheFolder = WORKING_FOLDER + "/spidercache_paginasamarillases";
	private AnonymousProxyManager proxyManager = null;
	int maxProxyThreads = 5;

	public SpiderPaginasAmarillasEs() throws IOException
	{
		//proxyManager = new AnonymousProxyManager(cachefolder, maxProxyThreads);
	}
	
	public void stop()
	{
		//proxyManager.stop();
	}

    private void processPage(String language, String pageContents, int maxBooks)
    {
        /*
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
		*/
    }

    void search(String group) throws IOException, InterruptedException
    {
        /////////////////////////////////////////////////////////
        // Parameters
        /////////////////////////////////////////////////////////
        String jobName = "paginasamarillases_" + group.replace("[\\-]", "_");
//        String workingfolder = "/home/xavi/.gvfs/processes/" + jobName;
        String workingFolder = WORKING_FOLDER + "/spider/" + jobName;
        int maxSpiderThreads = 20;
        int milisecondsBetweenQueries = 1000;

        /////////////////////////////////////////////////////////
        // Working Folders
        /////////////////////////////////////////////////////////
        FileHelper.createFolder(workingFolder);
        FileHelper.createFolder(cacheFolder);

        /////////////////////////////////////////////////////////
        // Create Spiders
        /////////////////////////////////////////////////////////
        List<Robot> spiderList = new ArrayList<Robot>();
        for(int i = 0; i < maxSpiderThreads; i++) {
            spiderList.add(new RobotPaginasAmarillasEs(workingFolder, cacheFolder, Long.MAX_VALUE));
        }

        /////////////////////////////////////////////////////////
        // Create Downloader
        /////////////////////////////////////////////////////////
        HttpDownloader downloader = new HttpDownloader(cacheFolder, 10 * 24 * 60, 10000, 60000);

        /////////////////////////////////////////////////////////
        // Add Seeds
        /////////////////////////////////////////////////////////
        String requestUrl = "http://www.paginasamarillas.es/search/" + group + "/all-ma/all-pr/all-is/all-ci/all-ba/all-pu/all-nc/1";
        String html = downloader.getRequest(requestUrl).toString();

        if (html!=null) {
            int numOfItems = Integer.parseInt("0" + RegExpHelper.getFirstMatch(html, "</strong></h1>&nbsp;\\(([0-9]+)\\)</div>", 1));
            for(int i = 1; i <= (numOfItems / 15)/*items per page*/ + 1; i++)
            {
                addNewSeed(new Seed("http://www.paginasamarillas.es/search/" + group + "/all-ma/all-pr/all-is/all-ci/all-ba/all-pu/all-nc/" + i, 100));
            }
        } else {
            System.out.println("error :"+requestUrl);
        }

        /////////////////////////////////////////////////////////
        // Start Agents
        /////////////////////////////////////////////////////////
        startAgentsManager(milisecondsBetweenQueries, proxyManager, spiderList);
        while(getSeedCount() > 0) Thread.sleep(50);
        stopAgentsManager();
        //proxyManager.stop();
    }
}
