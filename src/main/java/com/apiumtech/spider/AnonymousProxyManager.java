package com.apiumtech.spider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public class AnonymousProxyManager implements Runnable,SpiderConfig
{
	private List<Thread> m_threads = null;
	private List<Proxy> m_proxyList = null;
	private List<Proxy> m_checkedProxyList = new ArrayList<Proxy>();
	private int m_currentProxy = -1;
	private int m_poolSize = 0;

	public AnonymousProxyManager(String cacheFolder, int poolSize) throws IOException
	{
		m_proxyList = getProxyList(cacheFolder);
		m_poolSize = Math.max(4, poolSize);
		m_poolSize = Math.min(100, poolSize);
		m_threads = new ArrayList<Thread>();
		for(int i = 0; i < Math.max(1, poolSize / 2); i++)
		{
			m_threads.add(i, new Thread(this));
			m_threads.get(i).start();
		}
	}

	private List<Proxy> getProxies(String text, String pat)
	{
		List<Proxy> proxies = new ArrayList<Proxy>();
		List<List<String>> matchList = RegExpHelper.getMatches(text, pat, true);
		//
		for(int i = 0; i < matchList.size(); i++)
		{
			if(matchList.get(i).size() == 3)
			{
				String proxyhost = matchList.get(i).get(1);
				Integer proxyport = new Integer(matchList.get(i).get(2));
				System.out.println("Adding proxy " + i + "/" + matchList.size() + " " + proxyhost + ":" + proxyport);
				proxies.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyhost, proxyport)));
			}
		}

		return proxies;
	}

	private List<Proxy> cleanlist(List<Proxy> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			for(int j = 0; j < list.size(); j++)
			{
				if(i != j)
				{
					if(list.get(i) != null && list.get(j) != null)
					{
						if(list.get(i).toString().compareTo(list.get(j).toString()) == 0)
						{
							list.set(i, null);
							break;
						}
					}
				}
			}
		}
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i) == null)
				list.remove(i);
		}
		return list;
	}

	private List<Proxy> randomizelist(List<Proxy> list)
	{
		List<Proxy> newList = new ArrayList<Proxy>();
		while (list.size() > 0)
		{
			int i = (int) (Math.random() * (double) list.size());
			newList.add(list.get(i));
			list.remove(i);
		}
		return newList;
	}

	private String decodeProxyList_rosinstrumentcom(String str, long mod) throws UnsupportedEncodingException
	{
		// unescape
		for(int i = 16; i < 256; i++)
		{
			try
			{
				str = str.replaceAll("%" + Integer.toHexString(i), new Character((char) i).toString());
			}
			catch (StringIndexOutOfBoundsException e)
			{
				e.printStackTrace();
			}
		}

		byte[] bytes = str.getBytes();
		for(int i = 0; i < bytes.length; i++)
		{
			long modulus = Math.round(Math.sqrt(mod));
			bytes[i] = (byte) (bytes[i] ^ (byte) modulus);
		}
		String result = new String(bytes, "UTF-8");
		return result;
	}

	/*private List<Proxy> getProxyList_21(String cachefolder) throws IOException
	{
		HttpDownloader downloader = new HttpDownloader(cachefolder, 5000, 10000);
		String response = downloader.getRequest("http://xn--21-9kcepstre5j7bf.xn--p1ai/d/Free_Anonymous_Proxy_List.html", 0);
		List<Proxy> proxyList = getProxies(response, "([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}):([0-9]+)");
		System.out.println(proxyList.size() + " proxies has been found from http://xn--21-9kcepstre5j7bf.xn--p1ai/d/Free_Anonymous_Proxy_List.html");
		return proxyList;
	}*/

	private List<Proxy> getProxyList_rosinstrumentcom(String cacheFolder) throws IOException
	{
		HttpDownloader downloader = new HttpDownloader(cacheFolder, 60 * 24, 5000, 10000);
		String response = downloader.getRequest("http://tools.rosinstrument.com/proxy/?rule2").toString();
		FileHelper.stringToFile(response, WORKING_FOLDER+"/test.html");
		Long modulus = new Long(RegExpHelper.getFirstMatch(response, "Math.sqrt\\(([0-9]+)\\)", 1));
		String rawProxyList = RegExpHelper.getFirstMatch(response, "hideText\\('([^']*)'\\)", 1);
		rawProxyList = decodeProxyList_rosinstrumentcom(rawProxyList, modulus);
		List<Proxy> proxyList = getProxies(rawProxyList, "#([0-9a-zA-Z.-_]+):([0-9]+)#");
		System.out.println(proxyList.size() + " proxies has been found from http://tools.rosinstrument.com");
		return proxyList;
	}

	/*private List<Proxy> getProxyList_workingproxiesorg(String cachefolder) throws IOException
	{
		HttpDownloader downloader = new HttpDownloader(cachefolder, 5000, 10000);
		String pattern = "<font color=\"#333333\">([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})</font></td><td><font color=\"#333333\">([0-9]{2,5})[ \\r\\n\\t]*</font>";
		List<Proxy> proxyList = new ArrayList<Proxy>();
		for(int i = 1; i <= 10; i++)
		{
			String response = downloader.getRequest("http://www.proxyleech.com/page/" + i + ".php", 60 * 12);
			proxyList.addAll(getProxies(response, pattern));
		}
		System.out.println(proxyList.size() + " proxies has been found from http://www.proxyleech.com");
		return proxyList;
	}*/

	private List<Proxy> getProxyList(String cacheFolder) throws IOException
	{
		List<Proxy> proxyList = new ArrayList<Proxy>();
		//proxyList.addAll(getProxyList_workingproxiesorg(cachefolder));
		proxyList.addAll( getProxyList_rosinstrumentcom( cacheFolder ) );
		//		proxyList.addAll(getProxyList_21(cachefolder));
		proxyList = cleanlist(proxyList);
		System.out.println(proxyList.size() + " proxies has been found");
		return randomizelist(proxyList);
	}

	private boolean testProxy(Proxy proxy)
	{
		boolean r = false;
		try
		{
			HttpDownloader downloader = new HttpDownloader(null, -1, 5000, 10000);
			downloader.setProxy(proxy);
			String key = "" + Math.random();
			String response = downloader.getRequest("http://ca.wikipedia.org/w/index.php?title=Especial%3ACerca&search=" + key, -1).toString();
			if(response != null)
			{
				if(response.length() > 0)
				{
					if(response.indexOf("value=\"" + key + "\"") > -1)
					{
						r = true;
					}
				}
			}
		}
		catch (Exception e)
		{
			// e.printStackTrace( );
		}
		return r;
	}

	private Proxy getNextProxy()
	{
		synchronized (m_proxyList)
		{
			if(m_currentProxy == -1)
				m_currentProxy = (int) (Math.random() * (double) m_proxyList.size());
			m_currentProxy = (m_currentProxy >= m_proxyList.size() - 1) ? 0 : (m_currentProxy + 1);
		}
		return m_proxyList.get(m_currentProxy);
	}

	public Proxy getProxy()
	{
		Proxy proxy = null;
		synchronized (m_checkedProxyList)
		{
			if(m_checkedProxyList.size() > 0)
			{
				proxy = m_checkedProxyList.get(0);
				m_checkedProxyList.remove(0);
			}
		}
		return proxy;
	}
	
	public void stop()
	{
		for(int i = 0; i < m_threads.size(); i++)
			m_threads.get(i).interrupt();
	}

	public void run()
	{
		while (true)
		{
			if(m_checkedProxyList.size() < m_poolSize)
			{
				Proxy proxy = getNextProxy();
				if(proxy != null)
					if(testProxy(proxy))
					{
						synchronized (m_checkedProxyList)
						{
							m_checkedProxyList.add(proxy);
							System.out.println("Checked proxy list " + m_checkedProxyList.size() + "/" + m_poolSize);
						}
					}
			}
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				break;
			}
		}
	}
}
