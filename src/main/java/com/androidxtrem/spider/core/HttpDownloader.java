package com.androidxtrem.spider.core;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.RegExpHelper;

import java.io.*;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;


public class HttpDownloader
{
	private Proxy proxy = null;
	private String cacheFolder = null;
	private long minutesInCache = -1;
	private int connectionTimeout = 2000;
	private int readTimeout = 5000;

	public HttpDownloader(String cacheFolder, long minutesInCache, int connectionTimeout, int readTimeout)
	{
		cacheFolder = cacheFolder;
		minutesInCache = minutesInCache;
		connectionTimeout = connectionTimeout;
		readTimeout = readTimeout;
	}

	public void setProxy(Proxy proxy)
	{
		this.proxy = proxy;
	}

	public Proxy getProxy()
	{
		return this.proxy;
	}

	private long getMinutesSinceLastModified(String filePath)
	{
		File file = new File(filePath);
		Date date = new Date();
		long result = (date.getTime() - file.lastModified()) / 60000;
		return result;
	}

	public String getCacheFileName(String url)
	{
		return (cacheFolder + System.getProperty("file.separator") + url.replaceAll("[\\\\/:\\*\"\\?<>\\|]", "") + ".html").toLowerCase();
	}

	public boolean isInCache(String url, long minutesCache)
	{
		boolean result = false;

		if(url != null && cacheFolder != null)
		{
			String cachefile = getCacheFileName(url);
            boolean fileExists = FileHelper.fileExists(cachefile);
            result = minutesCache > -1 && fileExists && getMinutesSinceLastModified(cachefile) < minutesCache;
		}

		return result;
	}

	private void setInCache(String url, String page) throws IOException
	{
		if(url != null && cacheFolder != null)
		{
			String cachefile = getCacheFileName(url);
			FileHelper.stringToFile(page, cachefile);
		}
	}

	public String getFromCache(String url) throws IOException
	{
		String result = null;

		if(url != null && cacheFolder != null)
		{
			String cachefile = getCacheFileName(url);
			result = FileHelper.fileToString(cachefile);
		}

		return result;
	}

	public StringBuffer getRequest(String url) throws IOException
	{
		return getRequest(url, minutesInCache, true);
	}

    public StringBuffer getRequestXML(String url, long minutesInCache) throws IOException
    {
        return getRequest(url, minutesInCache, false);
    }

	public StringBuffer getRequest(String url, long minutesInCache, boolean setRequestProperties) throws IOException
	{
        StringBuffer sb = new StringBuffer();
		if(url != null)
		{
			if(isInCache(url, minutesInCache))
			{
				System.out.println("Page retrieved from cache [" + url + "]");
                String fromCache = getFromCache(url);
                return new StringBuffer(fromCache);
			}
			else
			{
				try
				{
					URLConnection conn = null;
                    URL urlObject = new URL(url);
                    if(proxy != null)
					{
						conn = urlObject.openConnection(proxy);
						conn.setConnectTimeout(connectionTimeout);
						conn.setReadTimeout(readTimeout);
					}
					else
					{
						conn = urlObject.openConnection();
						conn.setConnectTimeout(connectionTimeout);
						conn.setReadTimeout(readTimeout);
					}

                    if (setRequestProperties) setRequestProperties(conn);

//					String contentType = conn.getHeaderField("Content-Type").toUpperCase();
//					String charset = RegExpHelper.getFirstMatch(contentType, "CHARSET[ ]*=[ ]*([^ ;]+)", 1);
//					if(charset.length() == 0)
//					{
//						// System.out.println("Charset not found in headers " +
//						// conn.getHeaderFields( ).values( ).toString( ));
////						charset = "ISO-8859-1";
//						charset = "UTF-8";
//					}
                    String charset = "UTF-8";

					BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset/* "ISO-8859-1" */));

					char cbuf[] = new char[1024];
					int numOfReadChars = 0;
					while((numOfReadChars = rd.read(cbuf)) > 0 )
					{
						sb.append(cbuf, 0, numOfReadChars);
					}
					rd.close();

                    /*HttpURLConnection httpConnection = (HttpURLConnection)conn;
                    int code = httpConnection.getResponseCode();
                    if(code != 200)
                    {
                        System.out.println("Warning: url(" + url + ") has returned " + code);
                    }
                    else*/

                        if(minutesInCache > -1 && sb.length() > 0)
                        {
                            FileHelper.createFolder(cacheFolder);
                            setInCache(url, sb.toString());
                        }

				}
				catch(Exception e)
				{
					System.out.println("getRequest error: class(" + e.getClass().toString() + "), message(" + e.getMessage() + "), url(" + url + ")");
                    sb = null;
				}
			}
		}

		return sb;
	}


    private void setRequestProperties(URLConnection conn) {
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");
        conn.setRequestProperty("Referer", "https://mail.google.com/mail/u/0/#inbox");
    }

	public boolean getRequestBinary(String url, String outputFilePath) throws IOException
	{
		boolean r = false;

		if(url != null)
		{
			try
			{
				URLConnection conn = null;
				if(proxy != null)
				{
					conn = (new URL(url)).openConnection(proxy);
					conn.setConnectTimeout(connectionTimeout);
					conn.setReadTimeout(readTimeout);
				}
				else
				{
					conn = (new URL(url)).openConnection();
					conn.setConnectTimeout(connectionTimeout);
					conn.setReadTimeout(readTimeout);
				}

				conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");
				
				BufferedInputStream input = new BufferedInputStream(conn.getInputStream());
				BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(outputFilePath, false));
				
				r = true;
				
				byte[] buffer = new byte[(int)32768];
				int numOfReadChars = 0;
				while((numOfReadChars = input.read(buffer)) > 0)
				{
					output.write(buffer, 0, numOfReadChars);
				}
				
				input.close();
				output.close();
			}
			catch(Exception e)
			{
				r = false;
				
//				System.out.println("getRequest error: class(" + e.getClass().toString() + "), message(" + e.getMessage() + "), url(" + url + ")");
				
				File outputFile = new File(outputFilePath);
				if(outputFile.exists()) outputFile.delete();
			}
		}

		return r;
	}

	public String postRequest(String url, String outputData, long minutesInCache) throws IOException
	{
		String response = null;

		if(url != null)
		{
			if(isInCache(url, minutesInCache))
			{
				// System.out.println("Page retrieved from cache [" + url +
				// "]");
				return getFromCache(url);
			}
			else
			{
				try
				{
					URLConnection conn = null;
					if(proxy != null)
					{
						conn = (new URL(url)).openConnection(proxy);
						conn.setConnectTimeout(connectionTimeout);
						conn.setReadTimeout(readTimeout);
						conn.setDoOutput(true);
					}
					else
					{
						conn = (new URL(url)).openConnection();
						conn.setConnectTimeout(connectionTimeout);
						conn.setReadTimeout(readTimeout);
						conn.setDoOutput(true);
					}

					conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");

					conn.setRequestProperty("Referer", "http://www.dirigentesdigital.com/noticia/166590/TECNOLOG%C3%8DA/workmeter-desarrolla-primer-software-capaz-medir-productividad-personas.html");

					OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
					out.write(outputData);
					out.close();

					String contentType = conn.getHeaderField("Content-Type").toUpperCase();
					String charset = RegExpHelper.getFirstMatch(contentType, "CHARSET[ ]*=[ ]*([^ ;]+)", 1);
					if(charset.length() == 0)
					{
						// System.out.println("Charset not found in headers " +
						// conn.getHeaderFields( ).values( ).toString( ));
						charset = "ISO-8859-1";
					}

					BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset/* "ISO-8859-1" */));
					StringBuffer sb = new StringBuffer();
					String line;
					while((line = rd.readLine()) != null)
					{
						sb.append(line + "\n");
					}
					rd.close();

					response = sb.toString();
					// if(regExpManager.getFirstMatch( response, "(<.+>)+",
					// 1).length( ) == 0 ) throw new Exception( "Format Error");
					if(minutesInCache > -1)
					{
						FileHelper.createFolder(cacheFolder);
						setInCache(url, response);
					}
				}
				catch(Exception e)
				{
					response = null;
				}
			}
		}

		return response;
	}
}
