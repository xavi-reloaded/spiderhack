package com.androidxtrem.spider.agent;

import com.androidxtrem.spider.RegExpHelper;
import com.androidxtrem.spider.StringHelper;
import com.androidxtrem.spider.core.Robot;

import java.io.IOException;
import java.util.List;

public class RobotWordContext extends Robot
{
	private String m_word = "";

	public RobotWordContext(String word, String workingFolder, String cacheFolder, long minutesInCache) throws IOException, InterruptedException
	{
		super(workingFolder, cacheFolder, minutesInCache);
		m_word = word;
	}

	public void run() // throws IOException, InterruptedException
	{
		try
		{
			clearNewLinks();

			String response = getRequest(getUrl()).toString();
			if(response == null)
			{
				System.out.println("Error requesting \"" + getUrl() + "\" from \"" + getProxy() + "\"");
				setProxy(null);
			}
			else if(getUrl().matches("^http://www.google.com/.*"))
			{
				if(response.indexOf("Sorry, Google does not serve more than 1000 results for any query.") > -1)
				{
					System.out.println("Google error [Sorry, Google does not serve more than 1000 results for any query.]");
				}
				else if(response.indexOf("Our systems have detected unusual traffic from your computer network.") > -1)
				{
					System.out.println("Google error [Our systems have detected unusual traffic from your computer network.]");
					setProxy(null);
				}
				else
				{
					List<String> newLinks = RegExpHelper.getMatches(response, "\"(http://[^\"]+)\"", 1, true);
					for(int i = 0; i < newLinks.size();)
					{
						if(newLinks.get(i).matches(".*google.*")) newLinks.remove(i);
						else i++;
					}
					addNewLinks(newLinks);
				}
			}
			else
			{
				// clean text
				response = response.toLowerCase();
				response = response.replaceAll("<[ ]*a[ ]*href[^>]+>", " ");
				response = response.replaceAll("<[ ]*[/]?[ ]*([biua]|br)[ ]*>", " ");
				List<String> wordContexts = RegExpHelper.getMatches(response, ">([^>]*" + m_word + "[^>]*)<", 1, true);
				for(int i = 0; i < wordContexts.size(); i++)
				{
					String wordContext = wordContexts.get(i).trim();
					if(wordContext.length() > 0)
					{
						if(wordContext.matches("([^ {}/\\\\|]+[ ]+){9,}[^ {}/\\\\|]+"))
						{
							String text = StringHelper.cleanHtmlText(wordContext);
							saveResult(text, "" + text.hashCode());
						}
					}
					else
					{
						reportError("UnknownContextFormat", getUrl(), response);
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
