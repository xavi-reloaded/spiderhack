package com.androidxtrem.spider.parser;

import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 8/27/12
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class SeccionAmarillaMeFichaParserTest {

    private SeccionAmarillaMeFichaParser sut;

    @BeforeMethod
    public void setUp() throws Exception {
        sut = new SeccionAmarillaMeFichaParser();
    }

    @Test
    public void testParseTxtToHTml() throws Exception {

    }

    @Test
    public void test_getTitleFromPAHtml() throws Exception {
        String actual = sut.getTitleFromPAHtml(html);
        String expected = "JUSTO SIERRA";
        Assert.assertEquals(actual,expected,actual);
    }

    @Test
    public void test_getAddressFromPAHtml() throws Exception {
        String actual = sut.getAddressFromPAHtml(html);
        String expected = "JARDIN 300, DEL GAS";
        Assert.assertEquals(actual,expected,actual);
    }

    @Test
    public void test_getPostalCodeFromPAHtml() throws Exception {
        String actual = sut.getPostalCodeFromPAHtml(html);
        String expected = "02950";
        Assert.assertEquals(actual,expected,actual);
    }

    @Test
    public void test_getLocalityFromPAHtml() throws Exception {
        String actual = sut.getLocalityFromPAHtml(html);
        String expected = "DF";
        Assert.assertEquals(actual,expected,actual);
    }

    @Test
    public void test_getPhoneFromPAHtml() throws Exception {
        String actual = sut.getPhoneFromPAHtml(html);
        String expected = "TEL: (55)5148-3000";
        Assert.assertEquals(actual,expected,actual);
    }

    @Test
    public void test_getCategoriaFromPAHtml() throws Exception {
        String actual = sut.getCategoriaFromPAHtml(html);
        String expected = "Escuelas";
        Assert.assertEquals(actual,expected,actual);
    }


    private String html = "<div class=\"contact vcard\">\n" +
            "                                    <div class=\"titulo fn org organization-name\">\n" +
            "                                        <a  href=\"/informacion/justo-sierra/escuelas/distrito-federal/3568007\"  title=\"JUSTO SIERRA\"  class=\"ProductosMx\" accesskey=\"Informacion\" id=\"Mas-3568007\"> \n" +
            "                                        <span class=\"listado_destacado\">JUSTO SIERRA</span></a>\n" +
            "                                    </div>\n" +
            "                                        <div class=\"logo\">\n" +
            "                                            <a href=\"/informacion/justo-sierra/escuelas/distrito-federal/3568007\" accesskey=\"Imagen\" class=\"ProductosMx\" id=\"Img-3568007\">\n" +
            "                                                <img src=\"http://graficos.seccionamarilla.com.mx/98/E54807418.GIF\" alt=\"Escuelas-JUSTO-SIERRA-en-Distrito Federal-encuentralos-en-Sección-Amarilla-SPN\" \n" +
            "                                                title=\"Escuelas-JUSTO-SIERRA-en-Distrito Federal-encuentralos-en-Sección-Amarilla-SPN\" width=\"120px\" height=\"60px\"/>\n" +
            "                                            </a>\n" +
            "                                        </div> \n" +
            "                                    <div class=\"address adr\">\n" +
            "                                        <span class=\"street-address\">JARDIN 300, DEL GAS</span>, C.P&nbsp;\n" +
            "                                        <span class=\"postal-code\">02950</span>, <span class=\"locality\">DF</span>\n" +
            "                                    </div>\n" +
            "                                    <div class=\"phone\">\n" +
            "TEL: (55)5148-3000                                    </div>\n" +
            "                                        <div class=\"categoria_link\">\n" +
            "                                            <span>Categoria: </span><span class=\"catego\"><a href=\"javascript:\" onclick=\"Redirecciona('/resultados/escuelas/distrito-federal/1','escuelas','CAT_')\"; title=\"Escuelas\"  accesskey=\"Categoria\" class=\"ProductosMx\" id=\"Cat-3568007\">Escuelas</a></span>\n" +
            "                                        </div> \n" +
            "                                        <div class=\"web\">\n" +
            "                                            <a href=\"javascript:\" title=\"\" onclick=\"AbrirVentana('http://www.justosierra.com');\"class=\"ProductosMx\" accesskey=\"PaginaWeb\" id=\"Web-3568007\">pag. web</a>\n" +
            "                                        </div> \n" +
            "                                        <div class=\"mapa\">\n" +
            "                                        <a href=\"/mapas/justo-sierra/distrito-federal/3568007-anunciante\" title=\"Mapa\" class=\"ProductosMx\" accesskey=\"Mapa\" id=\"Map-3568007\">mapa</a>\n" +
            "                                        </div> \n" +
            "                                    <div class=\"salike\">\n" +
            "                                        <input  class=\"ButtonLike\" id=\"Like3568007\" type=\"image\" onclick=\"Like('3568007','JUSTO SIERRA');\" src=\"/Media/Default/images/Like.png\" title=\"Like\" style=\"width: 64px;\" onMouseOver=\"this.src='/Media/Default/images/Like_hover.png'\" onMouseOut=\"this.src='/Media/Default/images/Like.png'\"/>\n" +
            "                                        <input id=\"3568007\" type=\"text\" value=\"3\" class=\"salike_input\" disabled=\"disabled\"/>\n" +
            "                                    </div>\n" +
            "                                        <div class=\"info\">\n" +
            "                                            <a href=\"/informacion/justo-sierra/escuelas/distrito-federal/3568007\" title=\"Mas Info\" class=\"ProductosMx\" accesskey=\"Informacion_1\" id=\"Mas1-3568007\">+ info</a>\n" +
            "                                        </div>\n" +
            "                                </div>";


}
