package com.apiumtech.spider.agent;

import com.apiumtech.spider.AnonymousProxyManager;
import com.apiumtech.spider.Seed;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public class AgentsManager implements Runnable 
{

    protected Log log = LogFactory.getLog(AgentsManager.class);
    protected DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MMMM-yyyy:HH:ss:mm");

	// private int m_milisecondsBetweenQueries = 0;
	private AnonymousProxyManager m_proxyManager = null;
	private List<Agent> m_agentList = null;
	private List<Seed> m_seeds = new ArrayList<Seed>();
	private Thread m_thread = null;

	protected void startAgentsManager(int milisecondsBetweenQueries, AnonymousProxyManager proxyManager, List<Agent> agentList) throws InterruptedException, IOException
	{
		if(m_thread == null)
			m_thread = new Thread(this);
		if(!m_thread.isAlive())
		{
			// m_milisecondsBetweenQueries = milisecondsBetweenQueries;
			m_proxyManager = proxyManager;
			m_agentList = agentList;
			m_thread.start();
		}
	}

	protected int getSeedCount()
	{
		return m_seeds.size();
	}

	protected boolean addNewSeed(Seed newSeed)
	{
		boolean r = false;
		
		if(newSeed != null)
		{
			int i = 0;
			for(; i < m_seeds.size(); i++)
			{
				if(m_seeds.get(i).getSeed().compareTo(newSeed.getSeed()) == 0)
				{
					break;
				}
			}
			if(i == m_seeds.size())
			{
				m_seeds.add(newSeed);
				r = true;
			}
		}
		
		return r;
	}

	private int searchSeedIndex(String seed)
	{
		int r = -1;
		for(int i = 0; i < m_seeds.size(); i++)
		{
			if(m_seeds.get(i).getSeed().compareTo(seed) == 0)
			{
				r = i;
				break;
			}
		}
		return r;
	}

	public void run()
	{
		int i, j;
		while(true)
		{
			// get all new links from non alive spiders
			for(i = 0; i < m_agentList.size(); i++)
			{
				if(!m_agentList.get(i).isAlive())
				{
					List<String> newLinks = m_agentList.get(i).getNewLinks();
					if(newLinks.size() > 0)
					{
						int seedIndex = searchSeedIndex(m_agentList.get(i).getSeed());
						if(seedIndex > -1)
						{
							m_seeds.get(seedIndex).addLinks(newLinks);
						}
						m_agentList.get(i).clearNewLinks();
					}
				}
			}

			// assign proxies and links to spiders
			for(i = 0; i < m_agentList.size(); i++)
			{
				if(!m_agentList.get(i).isAlive())
				{
					// provide a exclusive proxy to processes
					if(m_proxyManager != null && m_agentList.get(i).getProxy() == null)
					{
						Proxy proxy = m_proxyManager.getProxy();
						if(proxy != null)
						{
							for(j = 0; j < m_agentList.size(); j++)
								if(m_agentList.get(j).getProxy() != null)
									if(m_agentList.get(j).getProxy().toString().compareTo(proxy.toString()) == 0)
										break;
							if(j == m_agentList.size())
								m_agentList.get(i).setProxy(proxy);
						}
					}
					// provide a link to processes
					if(m_proxyManager == null || m_agentList.get(i).getProxy() != null)
					{
						for(j = 0; j < m_seeds.size(); j++)
						{
							String link = m_seeds.get(j).getLink();
							if(link.length() > 0)
							{
								try
								{
									m_agentList.get(i).start(m_seeds.get(j).getSeed(), link);
								}
								catch(InterruptedException e)
								{
									e.printStackTrace();
								}
								break;
							}
						}
					}
				}
			}

			// clean seeds
			for(i = 0; i < m_seeds.size();)
			{
				if(m_seeds.get(i).getLinksCount() == 0)
				{
					for(j = 0; j < m_agentList.size(); j++)
					{
						if(m_agentList.get(j).isAlive())
						{
							if(m_agentList.get(j).getSeed().compareTo(m_seeds.get(i).getSeed()) == 0)
								break;
						}
					}
					if(j == m_agentList.size())
					{
						m_seeds.remove(i);
						continue;
					}
				}
				i++;
			}

			// avoid infinite loop
			try
			{
				Thread.sleep(50);
			}
			catch(InterruptedException e)
			{
				break;
			}
		}
	}

	public void stopAgentsManager() throws InterruptedException
	{
		if(m_thread != null)
		{
			m_thread.interrupt();
			m_thread = null;
		}
	}
}
