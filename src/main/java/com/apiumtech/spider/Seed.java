package com.apiumtech.spider;

import java.util.ArrayList;
import java.util.List;


public class Seed 
{
	private String m_seed = "";
	private int m_maxLinks = 3000;
	private int m_linksCount = 0;
	private List<String> m_links = new ArrayList<String>();

	public Seed(String url, int maxLinks)
	{
		reset(url, maxLinks);
	}

	public void reset(String url, int maxLinks)
	{
		m_seed = url;
		m_maxLinks = maxLinks;
		m_links.clear();
		m_links.add(url);
	}

	public String getSeed()
	{
		return m_seed;
	}

	public int getLinksCount()
	{
		return m_links.size();
	}

	public void addLinks(List<String> links)
	{
		if(m_linksCount < m_maxLinks)
		{
			m_linksCount += links.size();
			m_links.addAll(links);
		}
	}
	
	public void addLink(String link)
	{
		if(m_linksCount < m_maxLinks)
		{
			m_linksCount += 1;
			m_links.add(link);
		}
	}

	public String getLink()
	{
		String url = "";
		if(m_links.size() > 0)
		{
			url = m_links.get(0);
			m_links.remove(0);
		}
		return url;
	}
}
