package com.apiumtech.spider;

import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 7/30/12
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpDownloaderTest {
    private HttpDownloader sut;

    @BeforeMethod
    public void setUp() throws Exception {
        sut = new HttpDownloader("/media/DioCane/sd_spider/spidercache_paginasamarillases", 10 * 24 * 60, 10000, 60000);

    }

    @Test
    public void testSetProxy() throws Exception {

    }

    @Test
    public void testGetProxy() throws Exception {

    }

    @Test
    public void testIsInCache() throws Exception {

    }

    @Test
    public void testGetRequest() throws Exception {
        String url = "http://www.paginasamarillas.es/search/colegio-publico/all-ma/all-pr/all-is/all-ci/all-ba/all-pu/all-nc/1";
        StringBuffer actual = sut.getRequest(url, -1);
        Assert.assertTrue(actual.toString(), actual.toString().startsWith("\r\n<!DOCTYPE html PUBLIC"));
    }

    @Test
    public void testGetRequestBinary() throws Exception {

    }

    @Test
    public void testPostRequest() throws Exception {

    }

    @Test
    public void test_getCacheFileName_url_parsedUrlWritebleToFile() throws Exception {

        String url = "http://feeds.foxnews.com/foxnews/latests";
        String actual = sut.getCacheFileName(url);
        String expected = "/media/diocane/sd_spider/spidercache_paginasamarillases/httpfeeds.foxnews.comfoxnewslatests.html";
        Assert.assertEquals("",expected, actual);
    }

    @Test
    public void test_getFromCache() throws Exception {
        sut = new HttpDownloader("sd_spider/cache", 10 * 24 * 60, 10000, 60000);
        String url = "http://feeds.foxnews.com/foxnews/latests";
        String actual = sut.getFromCache(url);
        String expected = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><?xml-stylesheet type=\"text/xsl\" media=\"screen\" href=\"/~d/styles/rss2full.xsl\"?><?xml-stylesheet type=\"text/css\" media=\"screen\" href=\"http://feeds.foxnews.com/~d/styles/itemcontent.css\"?><rss xmlns:fnc=\"http://www.foxnews.com/xmlfeed/fncModule#\" version=\"2.0\"><channel>      <title>FOXNews.com</title><link>http://www.foxnews.com/</link><description>FOX News Channel - We Report. You Decide.</description><copyright>Copyright 2013 FOX News Channel</copyright><managingEditor>foxnewsonline@foxnews.com</managingEditor><language>en-us</language><lastBuildDate>Thu, 27 June 2013 04:38:10 EDT</lastBuildDate><webMaster>foxnewsonline@foxnews.com</webMaster><image><url>http://www.foxnews.com/images/headers/fnc_logo.gif</url><title>FOXNews.com Live Bookmark</title><link>http://www.foxnews.com/</link></image><atom10:link xmlns:atom10=\"http://www.w3.org/2005/Atom\" rel=\"self\" type=\"application/rss+xml\" href=\"http://feeds.foxnews.com/foxnews/latest\" /><feedburner:info xmlns:feedburner=\"http://rssnamespace.org/feedburner/ext/1.0\" uri=\"foxnews/latest\" /><atom10:link xmlns:atom10=\"http://www.w3.org/2005/Atom\" rel=\"hub\" href=\"http://pubsubhubbub.appspot.com/\" /><item><title><![CDATA['FLABBERGASTED': Son Given WWII Soldier's Letters 69 Years Later]]></title><link>http://www.foxnews.com/us/2013/06/26/california-man-flabbergasted-to-receive-father-world-war-ii-letters-6-years/</link><author>foxnewsonline@foxnews.com</author><description><![CDATA[<li><b><a href=\"http://www.foxnews.com/world/2013/06/24/germany-investigates-if-minnesota-man-was-commander-nazi-led-unit/\">Germany Investigates Minnesota Man Who Was Reportedly Nazi Commander</a></b></li><li><b><a href=\"http://www.foxnews.com/us/2013/06/26/suspected-mine-detonated-in-surf-off-new-jersey-beach/\">Suspected Mine Detonated in Surf Off New Jersey Beach</a></b></li>]]></description><pubDate>Thu, 27 Jun 2013 04:18:05 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA['IT'S HYPOCRISY':  Critics Slam Keystone Foe Over Oil Investment History]]></title><link>http://www.foxnews.com/politics/2013/06/26/critics-accuse-keystone-foe-hypocrisy-over-oil-investment-history/?test=latestnews</link><author>foxnewsonline@foxnews.com</author><description><![CDATA[<li><b><a href=\"http://video.foxnews.com/v/2509326577001/\">VIDEO: Is Billionaire Against Keystone Pipeline a Hypocrite? </a></b> <img src=\"/i/redes/icon-video.gif\"></li>]]></description><pubDate>Thu, 27 Jun 2013 04:18:05 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[TAKING THE FIFTH: IRS Manager Refuses to Testify at House Hearing]]></title><link>http://www.foxnews.com/politics/2013/06/27/senior-irs-manager-invokes-fifth-amendment-right/</link><author>foxnewsonline@foxnews.com</author><description><![CDATA[<li><b><a href=\"http://video.foxnews.com/v/2510347927001/\">VIDEO: Another Senior IRS Official Pleads the Fifth</a></b> <img src=\"/i/redes/icon-video.gif\"></li><li><b><a href=\"http://video.foxnews.com/v/2510711368001/\">VIDEO: Excuses, Excuses for Culture of Corruption at IRS</a></b> <img src=\"/i/redes/icon-video.gif\"></li>]]></description><pubDate>Thu, 27 Jun 2013 04:18:05 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Keystone Foe Investments Under Fire]]></title><link>http://www.foxnews.com/politics/2013/06/27/critics-accuse-keystone-foe-hypocrisy-over-oil-investment-history/</link><author>foxnewsonline@foxnews.com</author><description><![CDATA[Billionaire hedge fund manager and big Obama donor Tom Steyer is accused of hypocrisy for having financial interest in the death of Keystone XL &#8212; a project he opposes on environmental grounds.   <li><b><a href=\"http://video.foxnews.com/v/2509326577001/\">VIDEO: Is Billionaire Against Keystone Pipeline a Hypocrite? </a></b> <img src=\"/i/redes/icon-video.gif\"></li>  <li><b><a href=\"http://www.foxnews.com/us/2013/06/26/obama-administration-pushes-new-public-health-research-agenda-on-gun-control/\">Obama Administration Pushes New Public Health Research Agenda on Gun Control</a></b></li>  <li><b><a href=\"http://www.foxnews.com/politics/2013/06/26/muslim-scholar-tied-to-pro-hamas-group-radical-cleric-visits-white-house/\">Muslim Scholar Tied to Pro-Hamas Group, Radical Cleric Visits White House </a></b></li>  ]]></description><pubDate>Thu, 27 Jun 2013 04:18:05 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[TERMINAL CONDITION: Is Snowden Going to Be Stuck at Moscow Airport?]]></title><link>http://www.foxnews.com/politics/2013/06/26/snowden-stuck-in-moscow-us-russia-wrangle-over-leaker-status/</link><author>foxnewsonline@foxnews.com</author><description><![CDATA[<li><b><a href=\"http://www.foxnews.com/politics/2013/06/27/us-could-pull-millions-in-aid-to-ecuador-if-country-gives-snowden-asylum/\">US Aid to Ecuador Questioned Amid Snowden Asylum Bid</a></b></li>  <li><b><a href=\"http://video.foxnews.com/v/2507818954001/\">VIDEO: NSA Leak Fallout: World Losing Respect for US?</a></b> <img src=\"/i/redes/icon-video.gif\"></li>    ]]></description><pubDate>Thu, 27 Jun 2013 04:18:05 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Supreme Court allows benefits to same-sex couples - OPINION: Court issues two illegitimate decisions- OPINION: What marriage decision says about America]]></title><link>http://www.foxnews.com/politics/2013/06/26/supreme-court-strikes-down-defense-marriage-act-provision/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Pilots safe after F-16 fighter jet crashes in Arizona ]]></title><link>http://www.foxnews.com/us/2013/06/26/pilots-safe-after-f-16-fighter-jet-crashes-in-arizona/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Ailing Nelson Mandela on life support, family says- Notebook: Waiting outside Mandela's hospital]]></title><link>http://www.foxnews.com/world/2013/06/27/family-member-mandela-on-life-support/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Mich. man turns in mother as bank robbery suspect - Cops: Thief left behind birth certificate, note from mom]]></title><link>http://www.foxnews.com/us/2013/06/27/michigan-man-turns-mom-in-as-bank-robbery-suspect/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Body of NYC Sandy victim lay undetected for months]]></title><link>http://www.foxnews.com/us/2013/06/27/recently-discovered-sandy-victim-described-as-gentleman-by-neighbors/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Texas carries out its 500th execution since 1982]]></title><link>http://www.foxnews.com/us/2013/06/26/texas-carries-out-its-500th-execution-since-182/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[US boss held by Chinese workers leaves plant]]></title><link>http://www.foxnews.com/world/2013/06/27/american-boss-held-hostage-by-chinese-workers-leaves-plant-after-payout/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Ambassador Stevens reportedly warned of Libya 'security threats' in final diary entry]]></title><link>http://www.foxnews.com/politics/2013/06/26/stevens-reportedly-warned-libya-security-threats-in-final-diary-entry/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Ex-NFL star Aaron Hernandez accused of murder- Prominent defense attorney serving as co-counsel]]></title><link>http://www.foxnews.com/us/2013/06/26/former-new-england-patriot-aaron-hernandez-charged-with-murder/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[FOX NEWS POLL: Voters against arming Syrian rebels- FOX NEWS POLL: 66 percent worried about their health care under ObamaCare]]></title><link>http://www.foxnews.com/politics/2013/06/26/fox-news-poll-voters-disapprove-arming-syrian-rebels/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Witness in Zimmerman trial recounts teen's last call-  FULL COVERAGE: ZIMMERMAN TRIAL]]></title><link>http://www.foxnews.com/us/2013/06/26/judge-in-trayvon-martin-case-weighs-police-calls/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Wounded vets' custom-made bikes stolen in Alaska]]></title><link>http://www.foxnews.com/us/2013/06/27/wounded-vets-custom-cycles-stolen-in-anchorage/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Texas woman's identity unknown 3 years after suicide - Technology may aid 32-year-old Arizona cold case ]]></title><link>http://www.foxnews.com/us/2013/06/26/texas-woman-identity-remains-mystery-three-years-after-suicide/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[7 aboard American schooner missing in South Pacific]]></title><link>http://www.foxnews.com/world/2013/06/27/new-zealand-authorities-searching-for-american-boat-missing-in-south-pacific/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[NY court says Starbucks baristas must share tips]]></title><link>http://www.foxnews.com/us/2013/06/27/ny-top-court-starbucks-baristas-must-share-tips/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Murder conviction against US Marine overturned]]></title><link>http://www.foxnews.com/us/2013/06/26/murder-conviction-against-us-marine-overturned-1764670622/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:08 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Eva's Show Called 'Insulting']]></title><link>http://www.foxnews.com/entertainment/2013/06/26/eva-longorias-devious-maids-draws-criticism-cultural-backlash-for-negatively/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:14 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Windows 8.1: Is It Fixed Yet?]]></title><link>http://www.foxnews.com/tech/2013/06/26/microsoft-to-unveil-windows-81-at-build-conference-today/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:14 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Paparazzo Sues Justin Bieber]]></title><link>http://www.foxnews.com/entertainment/2013/06/27/paparazzo-sues-justin-bieber-for-alleged-assault/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:14 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Why Land Lines Still Rock]]></title><link>http://www.foxnews.com/tech/2013/06/26/sorry-cellphone-why-landlines-still-rock/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:14 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Did 'Today' Save Paula Deen?]]></title><link>http://www.foxnews.com/entertainment/2013/06/26/did-paula-deens-today-show-appearance-save-her-bacon/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:14 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[New App Shows What Jesus Saw]]></title><link>http://www.foxnews.com/tech/2013/06/26/biblical-history-comes-alive-augmented-reality-app/?vgnextrefresh=1</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:14 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[5 Scary Diseases Right Now  ]]></title><link>http://www.foxnews.com/health/2013/06/26/scariest-infectious-diseases-right-now/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:14 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[New Cars Most Dangerous?]]></title><link>http://www.foxnews.com/leisure/2013/06/26/study-finds-new-cars-more-likely-to-be-involved-in-accidents/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:14 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Nude Shot? You Shouldn't Have]]></title><link>http://www.foxnews.com/slideshow/entertainment/2013/06/26/nude-scenes-happened-farrah/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:14 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[DNA Decoding Record Broken]]></title><link>http://www.foxnews.com/science/2013/06/27/scientists-break-record-by-decoding-dna-700000-year-old-horse/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:14 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[How to Get Great Ribs]]></title><link>http://www.foxnews.com/leisure/2013/06/26/how-to-make-great-rack-ribs/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:14 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[One Direction Goes 3-D]]></title><link>http://video.foxnews.com/v/2510460257001/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:14 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item><item><title><![CDATA[Dirtiest US Beach Is...]]></title><link>http://www.foxnews.com/travel/2013/06/25/america-cleanest-and-dirtiest-beaches/</link><author>foxnewsonline@foxnews.com</author><description /><pubDate>Thu, 27 Jun 2013 04:18:14 EST</pubDate><fnc:datetime>04:18 AM EST</fnc:datetime></item></channel></rss>";
        Assert.assertEquals("",expected, actual);
    }
}
