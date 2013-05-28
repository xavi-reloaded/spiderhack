package com.apiumtech.spider.agent;

import com.apiumtech.spider.FileHelper;
import com.apiumtech.spider.RegExpHelper;

import java.io.IOException;

public class AgentGutenbergEpub extends Agent 
{
	public AgentGutenbergEpub(String workingFolder, String cacheFolder, long minutesInCache) throws IOException, InterruptedException
	{
		super(workingFolder, cacheFolder, minutesInCache);
	}

	public void run()
	{
		try
		{
			clearNewLinks();

			String url = getUrl();
			String html = getRequest(url);
			if(html == null)
			{
				System.out.println("Error requesting \"" + url + "\" from \"" + getProxy() + "\"");
				setProxy(null);
			}
			else
			{
				
				// href="http://www.gutenberg.org/ebooks/29240epub.images8"
				String bookUrl = RegExpHelper.getFirstMatch(html, "href=\"([^\"]+\\.epub\\.images)\"", 1);
				if(bookUrl == "") bookUrl = RegExpHelper.getFirstMatch(html, "href=\"([^\"]+\\.epub\\.noimages)\"", 1);
				if(bookUrl == "") bookUrl = RegExpHelper.getFirstMatch(html, "href=\"([^\"]+\\.epub)\"", 1);
				if(bookUrl == "")
				{
					reportError("InvalidBookPage", url, html);
				}
				else
				{
					String name = RegExpHelper.getFirstMatch(bookUrl, "/([^\"/]+\\.epub)", 1);
					String outputFile = getWorkingFolder() + System.getProperty("file.separator") + name;
					if(!FileHelper.fileExists(outputFile))
					{
						if(getRequestBinary(bookUrl, outputFile))
						{
							System.out.println("New result saved [" + outputFile + "] from [" + getSeed() + "]");
						}
						else
						{
							setProxy(null);
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
