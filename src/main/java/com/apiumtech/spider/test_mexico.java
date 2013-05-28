package com.apiumtech.spider;

import java.io.IOException;

public class test_mexico
{
	public static void main(String[] args) throws IOException, InterruptedException
	{

		SpiderSeccionAmarillaMe spider = new SpiderSeccionAmarillaMe();

        spider.search("escuelas"); //326

        spider.stop();
        spider = null;
	}

}
