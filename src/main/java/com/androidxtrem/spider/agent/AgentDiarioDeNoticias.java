package com.androidxtrem.spider.agent;

import com.androidxtrem.spider.RegExpHelper;
import com.androidxtrem.spider.StringHelper;

import java.io.IOException;
import java.util.List;

public class AgentDiarioDeNoticias extends Agent
{
	public AgentDiarioDeNoticias(String workingFolder, String cacheFolder, long minutesInCache) throws IOException, InterruptedException
	{
		super(workingFolder, cacheFolder, minutesInCache);
	}

	private boolean getArticle1(String url, String html) throws IOException, InterruptedException
	{
		boolean r = false;
		String[] parts = html.split("<div id=\"Article\">");
		if(parts.length > 1)
		{
			r = true;

			parts = parts[1].split("</div>");
			if(parts.length > 1)
			{
				String text = StringHelper.cleanHtmlText(parts[0]);
				saveResult(text, "" + text.hashCode());
			}
			else reportError("getArticle1", url, html);
		}
		return r;
	}


    private boolean getArticle2(String url, String html) throws IOException, InterruptedException
	{
		boolean r = false;
		String[] parts = html.split("<span id=\"ctl00_ctl00_bcr_maincontent_ThisContent\">");
		if(parts.length > 1)
		{
			r = true;

			parts = parts[1].split("<!-- TWITTER API -->");
			if(parts.length > 1)
			{
				String text = StringHelper.cleanHtmlText(parts[0]);
				saveResult(text, "" + text.hashCode());
			}
			else reportError("getArticle2", url, html);
		}
		return r;
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
			// day
			else if(getUrl().matches("http://www\\.dn\\.pt/pesquisa/default\\.aspx\\?data=[0-9]{1,2}-[0-9]{1,2}-[0-9]{4}"))
			{
				List<String> links = RegExpHelper.getMatches(html, "content_id=([0-9]+)", 1, true);
				for(int i = 0; i < links.size(); i++)
				{
					links.set(i, "http://www.dn.pt/Inicio/interior.aspx?content_id=" + links.get(i));
				}
				addNewLinks(links);
			}
			// article
			else if(url.matches("http://www\\.dn\\.pt/Inicio/interior\\.aspx\\?content_id=.*"))
			{
				if(!getArticle1(url, html)) if(!getArticle2(url, html))
				{
					reportError("unknownFormat", url, html);
				}
			}
			else reportError("unknownUrl", url, html);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
