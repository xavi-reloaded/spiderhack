package com.apiumtech.spider;

import java.io.IOException;

public class test2
{
	public static void main(String[] args) throws IOException, InterruptedException
	{

		/*SpiderGutenberg spider = new SpiderGutenberg();
		/spider.getLanguageBooks("catalan", "ca");
		/spider.getLanguageBooks("spanish", "es");
		spider.getLanguageBooks("english", "en", 100000);
		/spider.getLanguageBooks("german", "de");
		/spider.getLanguageBooks("italian", "it");
		/spider.getLanguageBooks("portuguese", "pt");
		/spider.getLanguageBooks("french", "fr");
		spider.stop();
		spider = null;*/

        SpiderPaginasAmarillasEs spider = new SpiderPaginasAmarillasEs();

//        spider.search("colegio-publico"); //326
//        spider.search("escuela-privado"); //28
//        spider.search("escuela-infantil"); //282
//        spider.search("instituto-de-enseñanza"); //40
//        spider.search("institutos-de-educacion-primaria"); //150
//        spider.search("institutos-de-educacion-secundaria"); //36
//        spider.search("colegio-bilingue-español-ingles"); //6
//        spider.search("colegio-mayor"); //13
//        spider.search("enseñanza-universitaria"); //198
//        spider.search("enseñanza-de-idioma"); //158
//        spider.search("curso-a-distancia"); //29
//        spider.search("centro-de-enseñanza"); //2143
//        spider.search("colegio-oficial-profesional"); //289
//        spider.search("escuela-infantil");
//        spider.search("colegio-privado");

//        spider.search("colegio-con-servicio-de-comedor");
//        spider.search("colegio-bilingue-español-ingles");
//        spider.search("residencia-escolar");
//
//
//        spider.search("academia-de-aleman"); //188)
//        spider.search("academia-de-baile"); //1424)
//        spider.search("academia-de-clase-de-apoyo"); //354)
//        spider.search("academia-de-dibujo-y-pintura"); //391)
//        spider.search("academia-de-formacion-profesional"); //430)
//        spider.search("academia-de-frances"); //224)
//        spider.search("academia-de-informatica"); //1740)
//        spider.search("academia-de-ingles"); //735)
//        spider.search("academia-de-modelo"); //24)
//        spider.search("academia-de-musica"); //1083)
//        spider.search("academia-de-peluqueria"); //973)
//        spider.search("preparacion-de-oposicion"); //843)



//        spider.search("enseñanza"); //843)
//        spider.search("academias-de-enseñanzas-diversas"); //3894)
//        spider.search("academia-de-diseño "); //137)
//        spider.search("academia-de-enseñanza-universitaria"); //491)
//        spider.search("academia-de-enseñanza-sanitaria"); //97)
//        spider.search("enseñanza-de-idioma"); //4893)

//        spider.search("centro-de-enseñanza"); //4893)
//        spider.search("enseñanza-universitaria"); //4893)
//        spider.search("enseñanza-de-idioma"); //4893)

        spider.stop();
        spider = null;
	}

}
