package com.androidxtrem.spider.agent;

import com.androidxtrem.spider.RegExpHelper;
import com.androidxtrem.spider.StringHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AgentPaginasAmarillasEs extends Agent
{
	public AgentPaginasAmarillasEs(String workingFolder, String cacheFolder, long minutesInCache) throws IOException, InterruptedException
	{
		super(workingFolder, cacheFolder, minutesInCache);
	}

    private int searchFaxes(List<String> urls) throws IOException
    {
        int numberOfFaxes = 0;

        for(int j = 0; j < urls.size(); j++)
        {
            String url = urls.get(j);
            //url= "http://www.becerrildelasierra.es/index.php?option=com_contact&task=view&contact_id=4&Itemid=3";
            String html = getRequest(url).toString();
            if(html == null)
            {
                //System.out.println("Error requesting URL level 2 (" + url + ") from proxy (" + getProxy() + ")");
                setProxy(null);
            }
            else
            {
                // fax list
                List<String> faxes = new ArrayList<String>();

                // getNormalizedMessage html
                //html = html.toLowerCase();
                html = StringHelper.unescapeHTML(html);

                // fax title regexp
                String regExp_faxTitle = "([telefon]{1,8}[ ]*[/y])*[ ]*(fax|telefax)";

                // fax number regexp
                String regExp_faxNumber = "[ /0-9\\\\#{}\\+\\*\\-\\()\\[\\]\\.]+[0-9][ /0-9\\\\#{}\\+\\*\\-\\()\\[\\]\\.]+";

                // format1: "<img src='...fax.jpg'> <...> <...> XXXXXXXXX"
                String regExp1 = "<[ ]*img[^<]+src[ ]*=[ ]*[\"']([^\"']*fax[^\"']+)[\"'][^<]*>(<[^>]+>|[ \\t\\r\\n])*(" + regExp_faxNumber + ")";
                faxes.addAll(RegExpHelper.getMatches(html, regExp1, 3, true, Pattern.CASE_INSENSITIVE));

                // html to plain text
                html = StringHelper.cleanHtmlText(html);

                // format2: "fax: XXXXXXXXX", "telefax: XXXXXXXXX", "tel/fax: XXXXXXXXX/XXXXXXXXX"
                String regExp2 = "[^a-z]*" + regExp_faxTitle + "[ ]*[:\\.][ ]*(" + regExp_faxNumber + ")";
                faxes.addAll(RegExpHelper.getMatches(html, regExp2, 3, true, Pattern.CASE_INSENSITIVE));

                // trim
                for(int i = 0; i < faxes.size(); i++)
                {
                    faxes.set(i, faxes.get(i).trim());
                    if(!faxes.get(i).matches(".*[0-9].*"))
                    {
                        faxes.remove(i);
                        i++;
                    }
                }

                // saveFeed results
                for(int i = 0; i < faxes.size(); i++)
                {
                    String fax = faxes.get(i);
                    //System.out.println("Info: New FAX (" + fax + ") from URL (" + url + ")");

                    // filename: "website_faxXXXXXXXXX"
                    String filename = url.replaceAll("^([^/]+//[^/]+)/.*$", "$1").replaceAll("[:\\./]", "_") + "_fax" + fax.replaceAll("[^0-9]", "") + ".txt";
                    // data: "url[]\nfax[]"
                    String data = "url[" + url + "]\nfax[" + fax + "]";
                    // saveFeed
                    saveResult(data, filename);
                    // mark
                    numberOfFaxes++;
                }
            }
        }
        return numberOfFaxes;
    }

    private String completeUrl(String url, String link)
    {
        String r = link;
        if(!link.matches("^[a-zA-Z]{2,5}://.*"))
        {
            // absolute ?
            if(link.length() > 0)
            {
                if(link.charAt(0) == '/')
                {
                    r = url.replaceAll("([^/]+//[^/]+).*", "$1") + link;
                }
                else
                {
                    r = url.replaceAll("/$", "") + "/" + link.replaceAll("^/", "");
                }
            }
        }
        return r;
    }

    private List<String> completeUrls(String url, List<String> links)
    {
        List<String> r = new ArrayList<String>();
        for(int i = 0; i < links.size(); i++) r.add(i, completeUrl(url, links.get(i)));
        return r;
    }

    private List<String> unescapeUrls(List<String> urls)
    {
        List<String> r = new ArrayList<String>();
        for(int i = 0; i < urls.size(); i++) r.add(StringHelper.unescapeHTML(urls.get(i)));
        return r;
    }

	public void run()
	{
		try
		{
			clearNewLinks();
			String url = getUrl();

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			if(url.matches("^http://www\\.paginasamarillas\\.es.*")) ////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			{
                String html = getRequest(url).toString();
                if(html == null)
                {
                    //System.out.println("Error requesting URL (" + url + ") from proxy (" + getProxy() + ")");
                    setProxy(null);
                }
                else
                {
                    // search for websites
                    List<String> newLinks = RegExpHelper.getMatches(html, "<a[^>]+class=\"telefono\"[^>]+http://www.paginasamarillas.es/jump\\?dest=([^&]+)", 1, true);
                    for(int i = 0; i < newLinks.size(); i++)
                    {
                        String newLink = newLinks.get(i);
                        newLink = StringHelper.unescapeHTML(newLink);
                        if(!newLink.matches("^http://.*"))
                        {
                            newLink = "http://" + newLink;
                        }
                        newLinks.set(i, "1_" + newLink);
                        //System.out.println("Info: New URL level 1 (" + newLink + ")");
                    }
                    // add new seeds
                    addNewLinks(newLinks);
                }
            }
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            else if(url.matches("^1_.*")) ///////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {
                List<String> newLinks = new ArrayList<String>();

                url = url.substring(2);
                //url = "http://www.colegioaltair.com";
                //url = "http://www.colegioaltair.com/altair/2/";
                //url = "http://www.escuelainfantilelvalle.com";
                //url = "http://www.colegiosanjose.net";
                //url = "http://www.bell-lloc.org";
                //url = "http://www.bell-lloc.org/index.php";
                //url = "http://www.consaburum.com";
                //url = "http://edu.jccm.es/ies/consaburum";
                //url = "http://www.ayuntamientodecubasdelasagra.es";
                String html = getRequest(url).toString();
                if(html == null)
                {
                    //System.out.println("Error requesting URL level 1 (" + url + ") from proxy (" + getProxy() + ")");
                    setProxy(null);
                }
                else
                {
                    // add new seeds
                    List<String> newSeeds = new ArrayList<String>();
                    newSeeds.addAll(RegExpHelper.getMatches(html, "<meta[^>]+http-equiv[ ]*=[ ]*[\"']refresh[\"'][^>]+url[ ]*=[ ]*[\"']?([^ '\";>]+)[^>]*>", 1, true, Pattern.CASE_INSENSITIVE));
                    newSeeds.addAll(RegExpHelper.getMatches(html, "<[ ]*frame [^>]*src[ ]*=[ ]*[\"']([^\"']+)[\"'][^>]*>", 1, true, Pattern.CASE_INSENSITIVE));
                    newSeeds = completeUrls(url, newSeeds);
                    newSeeds = unescapeUrls(newSeeds);
                    for(int i = 0; i < newSeeds.size(); i++) if(newSeeds.get(i).compareToIgnoreCase(url) == 0)
                    {
                        newSeeds.remove(i);
                        i--;
                    }
                    for(int i = 0; i < newSeeds.size(); i++) newSeeds.set(i, "1_" + newSeeds.get(i));
                    addNewLinks(newSeeds);

                    // get all links
                    newLinks.addAll(RegExpHelper.getMatches(html, "<[ ]*a [^>]*href[ ]*=[ ]*[\"']([^\"']+)[\"'][^>]*>", 1, true, Pattern.CASE_INSENSITIVE));
                    newLinks.addAll(RegExpHelper.getMatches(html, "<[ ]*iframe [^>]*src[ ]*=[ ]*[\"']([^\"']+)[\"'][^>]*>", 1, true, Pattern.CASE_INSENSITIVE));
                    newLinks.addAll(RegExpHelper.getMatches(html, "(document|window)\\.?location(.href)*[ ]*=[ ]*['\"\\\\]+([^'\"\\\\]+)['\"\\\\]+", 3, true, Pattern.CASE_INSENSITIVE));

                    // trim
                    for(int i = 0; i < newLinks.size(); i++)
                    {
                        newLinks.set(i, newLinks.get(i).trim());
                    }

                    // filter external links
                    for(int i = 0; i < newLinks.size(); i++)
                    {
                        // is external link ?
                        if(newLinks.get(i).matches("^[a-zA-Z]{2,5}://.*"))
                        {
                            // is external link but same website ?
                            if(newLinks.get(i).length() > url.length())
                            {
                                String chunk = newLinks.get(i).substring(0, url.length());
                                if(chunk.compareTo(url) == 0)
                                {
                                    newLinks.set(i, newLinks.get(i).substring(url.length()));
                                    continue;
                                }
                            }
                            // remove
                            newLinks.remove(i);
                            i--;
                        }
                    }

                    // filter other links
                    for(int i = 0; i < newLinks.size(); i++)
                    {
                        String link = newLinks.get(i).toLowerCase();
                        if( link.matches("^javascript.*") ||
                            link.matches("^mailto.*") ||
                            link.matches("^#.*") )
                        {
                            newLinks.remove(i);
                            i--;
                        }
                    }

                    // unescape links
                    newLinks = unescapeUrls(newLinks);

                    // log
                    if(newLinks.size() == 0)
                    {
                        System.out.println("Warning, No links found in website (" + url + ")");
                    }

                    // complete urls
                    newLinks = completeUrls(url, newLinks);

                    // add url base as link
                    newLinks.add(url);

                    // search for faxes
                    int faxesFound = searchFaxes(newLinks);
                    System.out.println("Info: " + faxesFound + " faxes found in website (" + url + ")");
                }

				//reportError("InvalidBookPage", url, html);
			}
            else
            {
                reportError("InvalidURL", url, "");
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
}
