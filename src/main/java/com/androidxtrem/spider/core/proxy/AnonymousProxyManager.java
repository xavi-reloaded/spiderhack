package com.androidxtrem.spider.core.proxy;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public class AnonymousProxyManager extends ProxyListManager implements Runnable
{
	protected List<Thread> threads = null;
	protected List<Proxy> proxyList = null;
	protected List<Proxy> validatedProxyList = new ArrayList<Proxy>();
    protected int currentProxy = -1;
	protected int poolSize = 0;

	public AnonymousProxyManager(String cacheFolder, int poolSize) throws IOException
	{
		proxyList = getProxyList(cacheFolder);
		this.poolSize = Math.max(4, poolSize);
		this.poolSize = Math.min(100, poolSize);
		threads = new ArrayList<Thread>();
		for(int i = 0; i < Math.max(1, poolSize / 2); i++)
		{
			threads.add(i, new Thread(this));
			threads.get(i).start();
		}
	}

    public void run()
    {
        while (true)
        {
            if(validatedProxyList.size() < poolSize)
            {
                Proxy proxy = getNextProxy();
                if(proxy != null)
                    if(testProxy(proxy))
                    {
                        synchronized (validatedProxyList)
                        {
                            validatedProxyList.add(proxy);
                            System.out.println("Checked proxy list " + validatedProxyList.size() + "/" + poolSize);
                        }
                    }
            }
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                System.out.println("Message !!! " + validatedProxyList.size() + "/" + poolSize);
                break;
            }
        }
    }

	protected Proxy getNextProxy()
	{
		synchronized (proxyList)
		{
			if(currentProxy == -1)
				currentProxy = (int) (Math.random() * (double) proxyList.size());
			currentProxy = (currentProxy >= proxyList.size() - 1) ? 0 : (currentProxy + 1);
		}
		return proxyList.get(currentProxy);
	}

	public Proxy getProxy()
	{
		Proxy proxy = null;
		synchronized (validatedProxyList)
		{
			if(validatedProxyList.size() > 0)
			{
				proxy = validatedProxyList.get(0);
				validatedProxyList.remove(0);
			}
		}
		return proxy;
	}

	public void stop()
	{
		for(int i = 0; i < threads.size(); i++)
			threads.get(i).interrupt();
	}
}
