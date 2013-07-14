package com.apiumtech.spider.agent;

import com.androidxtrem.commonsHelpers.RegExpHelper;

import java.io.IOException;

public class AgentGutenberg extends Agent 
{
	public AgentGutenberg(String workingFolder, String cacheFolder, long minutesInCache) throws IOException, InterruptedException
	{
		super(workingFolder, cacheFolder, minutesInCache);
	}

	public void run()
	{
		try
		{
			clearNewLinks();

			String url = getUrl();
			String html = getRequest(url).toString();
			if(html == null)
			{
				System.out.println("Error requesting \"" + url + "\" from \"" + getProxy() + "\"");
				setProxy(null);
			}
			else
			{
				//Thread.sleep(5000);
				
				//href="http://www.gutenberg.org/ebooks/29240.txt.utf8"
				String bookUrl = RegExpHelper.getFirstMatch(html, "href=\"([^\"]+\\.txt\\.utf8)\"", 1);
				if(bookUrl == "")
				{
					reportError("InvalidBookPage", url, html);
				}
				else
				{
					html = getRequest(bookUrl).toString();
					if(html == null)
					{
						System.out.println("Error requesting \"" + url + "\" from \"" + getProxy() + "\"");
						reportError("InvalidBookLink", url, html);
						//setProxy(null);
					}
					else
					{
						String name = RegExpHelper.getFirstMatch(bookUrl, "/([^\"/]+)\\.txt\\.utf8", 1);
						saveResult(html, name);
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
