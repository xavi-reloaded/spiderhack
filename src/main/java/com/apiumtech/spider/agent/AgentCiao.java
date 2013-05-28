package com.apiumtech.spider.agent;

import com.apiumtech.spider.FileHelper;
import com.apiumtech.spider.HttpDownloader;
import com.apiumtech.spider.RegExpHelper;
import com.apiumtech.spider.StringHelper;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public class AgentCiao implements Runnable 
{
	private String m_seed = "";
	private String m_link = "";
	private List<String> m_newLinks = new ArrayList<String>();
	private Thread m_thread = null;
	private String m_workingFolder = "";
	private long m_minutesInCache = -1;
	private HttpDownloader m_downloader = null;
	private int m_milisecondsBetweenQueries = 1000;

	public AgentCiao(String cacheFolder)
	{
		m_downloader = new HttpDownloader(cacheFolder, -1, 10000, 20000);
	}

	public void start(String seed, String url, String workingFolder, long minutesInCache, int milisecondsBetweenQueries) throws IOException, InterruptedException // throws
	// IOException,
	// InterruptedException
	{
		if(m_thread != null && m_thread.isAlive())
			throw new IllegalThreadStateException();
		if(url != null && url.length() > 0)
		{
			m_seed = seed;
			m_link = url;
			m_workingFolder = workingFolder;
			FileHelper.createFolder(m_workingFolder);
			m_minutesInCache = minutesInCache;
			m_milisecondsBetweenQueries = milisecondsBetweenQueries;
			m_thread = new Thread(this);
			m_thread.start();
		}
	}

	public String getSeed()
	{
		return m_seed;
	}

	public boolean isAlive()
	{
		return (m_thread != null) ? m_thread.isAlive() : false;
	}

	public List<String> getNewLinks()
	{
		return m_newLinks;
	}

	public void clearNewLinks()
	{
		m_newLinks.clear();
	}

	public void setProxy(Proxy proxy)
	{
		m_downloader.setProxy(proxy);
	}

	public Proxy getProxy()
	{
		return m_downloader.getProxy();
	}

	private void reportError(String name, String html) throws IOException, InterruptedException
	{
		String folder = m_workingFolder + "\\errors\\" + name;
		FileHelper.createFolder(folder);
		String file = folder + "\\" + m_link.replaceAll("[\\\\/:\\*\"\\?<>\\|]", "") + ".html";
		if(!FileHelper.fileExists(file))
		{
			FileHelper.stringToFile(html, file);
		}
	}

	public void run()
	{
		try
		{
			m_newLinks.clear();
			String response = m_downloader.getRequest(m_link, m_minutesInCache);
			if(response == null)
			{
				System.out.println("Error requesting \"" + m_link + "\" from \"" + m_downloader.getProxy() + "\"");
				m_downloader.setProxy(null);
			}
			else if(m_link.matches("^http://www.google.com/.*"))
			{
				if(response.indexOf("Sorry, Google does not serve more than 1000 results for any query.") > -1)
				{
					System.out.println("Google error [Sorry, Google does not serve more than 1000 results for any query.]");
				}
				else if(response.indexOf("Our systems have detected unusual traffic from your computer network.") > -1)
				{
					System.out.println("Google error [Our systems have detected unusual traffic from your computer network.]");
					m_downloader.setProxy(null);
				}
				else
				{
					m_newLinks = RegExpHelper.getMatches(response, "(http://[a-z0-9\\.]+\\.ciao\\.[^&\"]+)", 1, true);
				}
			}
			else
			{
				boolean commentsFound = false;

				// partial comments
				// ----------------------------------------------------------------------------------
				List<List<String>> ciaoNewLinks = RegExpHelper.getMatches(response, "<img[^>]*stars([0-9]+)\\.gif[^>]*>.{0,150}<a[^>]*href=\"(http[^\"]*)\"[^>]*>", true);
				for(int j = 1; j < ciaoNewLinks.size(); j++)
				{
					commentsFound = true;
					if(ciaoNewLinks.get(j).get(2).length() > 0)
					{
						if(!m_downloader.isInCache(ciaoNewLinks.get(j).get(2), m_minutesInCache))
							m_newLinks.add(ciaoNewLinks.get(j).get(2));
					}
				}

				// dedicated comment page
				// ----------------------------------------------------------------------------------
				String[] ciaoRawComments = response.split("CWReviewsContent");
				if(ciaoRawComments.length > 1)
				{
					commentsFound = true;
					String ciaoRating = RegExpHelper.getFirstMatch(ciaoRawComments[1], "<img[^>]*stars([0-9]+)\\.gif[^>]*>", 1);
					String[] aux = ciaoRawComments[1].split("<[^>]*class=\"reviewText\"[^>]*>");
					if(aux.length == 2)
					{
						aux = aux[1].split("</div>");
						if(aux.length >= 2)
						{
							String ciaoCleanComment = StringHelper.cleanHtmlText(aux[0]);
							FileHelper.createFolder(m_workingFolder + "\\" + ciaoRating);
							String commentFile = m_workingFolder + "\\" + ciaoRating + "\\" + ciaoCleanComment.hashCode() + ".html";
							if(!FileHelper.fileExists(commentFile))
							{
								FileHelper.stringToFile(ciaoCleanComment, commentFile);
								System.out.println("New comment saved [" + commentFile + "] dedicated");
							}
						}
						else
						{
							reportError("UnknownDedicatedCommentFormat", response);
						}
					}
					else
					{
						reportError("UnknownDedicatedPageFormat", response);
					}
				}

				// result
				// ----------------------------------------------------------------------------------
				if(!commentsFound)
					reportError("NoCommentsFound", response);
			}
			Thread.sleep(m_milisecondsBetweenQueries);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
