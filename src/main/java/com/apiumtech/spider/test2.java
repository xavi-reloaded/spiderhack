package com.apiumtech.spider;

import java.io.IOException;

public class test2
{
	public static void main(String[] args) throws IOException, InterruptedException
	{

        SpiderPaginasAmarillasEs spider = new SpiderPaginasAmarillasEs();

//        processes.search("centro-de-enseñanza"); //4893)
//        processes.search("enseñanza-universitaria"); //4893)
//        processes.search("enseñanza-de-idioma"); //4893)

        spider.stop();
        spider = null;
	}

}
