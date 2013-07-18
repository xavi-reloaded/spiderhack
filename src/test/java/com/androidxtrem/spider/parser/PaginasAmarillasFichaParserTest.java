package com.androidxtrem.spider.parser;

import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 8/6/12
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaginasAmarillasFichaParserTest {


    PaginasAmarillasFichaParser sut;
    @BeforeMethod
    public void setUp() throws Exception {
        sut = new PaginasAmarillasFichaParser();
    }

    @Test
    public void testParseTxtToHTml() throws Exception {

    }

    @Test
    public void testGetTitleFromPAHtml() throws Exception {
        String actual = sut.getTitleFromPAHtml(html);
        String expected = "EXCURSIONES MARÍTIMAS JOVEN ANA BELÉN";
        Assert.assertEquals(actual,expected,actual);
    }

    @Test
    public void test_getAddressFromPAHtml() throws Exception {
        String actual = sut.getAddressFromPAHtml(html);
        String expected = "Avenida Mariano Ballester, s/n (Embarcadero del CAR - Pº Marítimo de los Narejos)";
        Assert.assertEquals(actual,expected,actual);
    }

    @Test
    public void test_getPostalCodeFromPAHtml() throws Exception {
        String actual = sut.getPostalCodeFromPAHtml(html);
        String expected = "30740";
        Assert.assertEquals(actual,expected,actual);
    }

    @Test
    public void test_getLocalityFromPAHtml() throws Exception {
        String actual = sut.getLocalityFromPAHtml(html);
        String expected = "LOS ALCAZARES";
        Assert.assertEquals(actual,expected,actual);
    }

    @Test
    public void test_getRegionFromPAHtml() throws Exception {
        String actual = sut.getRegionFromPAHtml(html);
        String expected = "(MURCIA)";
        Assert.assertEquals(actual,expected,actual);
    }

    @Test
    public void test_getEnterpriseDataFromPAHtml() throws Exception {
        String actual = sut.getEnterpriseDataFromPAHtml(html);
        String expected = "Excursiones marítimas hasta 200 personas. Fiestas privadas, despedidas, cumpleaños, celebraciones... Paseos marítimos por el Mar Menor y el Mediterráneo.";
        Assert.assertEquals(actual,expected,actual);
    }

    @Test
    public void test_getPhoneDataFromPAHtml() throws Exception {
        String actual = sut.getPhoneDataFromPAHtml(html);
        // out or order !
        String expected = null;
        Assert.assertEquals(actual,expected,actual);
    }

    @Test
    public void test_getParamsFromPAHtml() throws Exception {
        String actual = sut.getParamsFromPAHtml(html);
        String expected = "\"paol2086113324Xbfd08a715a94d0cc\",\"006\",\"39820494M\",\"G3\",\"11\",\"PAM\",\"IL\",\"\",\"\"";
        Assert.assertEquals(actual,expected,actual);
    }

    @Test
    public void test_getEnterpriseIdPAHtml() throws Exception {
        String str = "\"paol2086113324Xbfd08a715a94d0cc\",\"006\",\"39820494M\",\"G3\",\"11\",\"PAM\",\"IL\",\"\",\"\"";
        String actual = sut.getEnterpriseIdFromParamArguments(str);
        String expected = "39820494M-006";
        Assert.assertEquals(actual,expected,actual);
    }





    private String html = "<div id=\"ficha11\" class=\"cont\">\n" +
            "<div class=\"izq\">\n" +
            "    <div class=\"iconos\">\n" +
            "\n" +
            "        <!-- Solo si es busqueda organica, incluimos el poi en los listados -->\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "        <a class=\"lenteja\">\n" +
            "            J\n" +
            "\n" +
            "        </a>\n" +
            "\n" +
            "\n" +
            "        <script type=\"text/javascript\">\n" +
            "            addPoisResultSpecific(\"P\", \"true\", \"10\", \"39820494M\", \"006\",\"37.751274\", \"-0.838075\", null, null);\n" +
            "            setSrcIframe();\n" +
            "        </script>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "    </div>\n" +
            "    <div class=\"microficha\">\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "        <a\n" +
            "\n" +
            "                class=\"tit PaolClick omn_fWeb_11\"\n" +
            "\n" +
            "                href=\"http://www.paginasamarillas.es/jump?dest=http%3A%2F%2Fwww.crucerosmarmenor.net&amp;t=IL&amp;producto=PAO&amp;c=39820494M&amp;a=006&amp;gp_orden=G3&amp;seg=32379c48c3ef5204d3cd211147edf70844ffa1d5&amp;posicion=11&id_busq=paol2086113324Xbfd08a715a94d0cc&site=paol&mode=SIMPLE&pext=\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "                target=\"_blank\" rel=\"nofollow\" title=\"Se abre en una nueva ventana\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "                >EXCURSIONES MARÍTIMAS JOVEN ANA BELÉN</a>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "        <p class=\"adr\">\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "            Avenida Mariano Ballester, s/n (Embarcadero del CAR - Pº Marítimo de los Narejos)\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "            ,\n" +
            "            <span class=\"postal-code\">30740</span>\n" +
            "\n" +
            "\n" +
            "\n" +
            "            ,\n" +
            "            LOS NAREJOS\n" +
            "\n" +
            "\n" +
            "\n" +
            "            ,\n" +
            "            <span class=\"locality\">LOS ALCAZARES</span>&nbsp;<span class=\"region\">(MURCIA)</span>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "            <br/>\n" +
            "        <p>\n" +
            "\n" +
            "            <a class=\"url fn n PaolClick omn_fWeb_11\"\n" +
            "               href=\"http://www.paginasamarillas.es/jump?dest=http%3A%2F%2Fwww.crucerosmarmenor.net&amp;t=IL&amp;producto=PAO&amp;c=39820494M&amp;a=006&amp;gp_orden=G3&amp;seg=32379c48c3ef5204d3cd211147edf70844ffa1d5&amp;posicion=11&id_busq=paol2086113324Xbfd08a715a94d0cc&site=paol&mode=SIMPLE&pext=\" target=\"_blank\" title=\"Se abre en una nueva ventana\" rel=\"nofollow\" name=\"urlCliente\">http://www.crucerosmarmenor.net</a>\n" +
            "\n" +
            "\n" +
            "        </p>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "        </p>\n" +
            "\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"pest-vcard\">\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "    </div>\n" +
            "    <div class=\"caja\">\n" +
            "\n" +
            "        <div class=\"js_hide\">Datos</div>\n" +
            "        <h2><a class=\"seo\" href=\"http://www.paginasamarillas.es/fichas/excursiones-maritimas-joven-ana-belen_39820494M_006.html\" onclick=\"javascript:writeFichMoxqumCookie('','11','G3','IL');\" >EXCURSIONES MARÍTIMAS JOVEN ANA BELÉN</a></h2>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "        <br> Excursiones marítimas hasta 200 personas.\n" +
            "        <br> Fiestas privadas, despedidas, cumpleaños, celebraciones...\n" +
            "        <br> Paseos marítimos por el Mar Menor y el Mediterráneo.\n" +
            "\n" +
            "\n" +
            "    </div>\n" +
            "</div>\n" +
            "<div class=\"der\" id=\"divDerecha11\">\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "</div>\n" +
            "<div class=\"clear\"></div>\n" +
            "<ul id=\"tabsFooters11\" class=\"footer\">\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "    <li class=\"primer\"><a target=\"_blank\" rel=\"nofollow\" title=\"Se abre en una nueva ventana\"\n" +
            "                          class=\"web PaolClick omn_fWeb_11\"\n" +
            "                          id=\"u11\" href=\"http://www.paginasamarillas.es/jump?dest=http%3A%2F%2Fwww.crucerosmarmenor.net&amp;t=IL&amp;producto=PAO&amp;c=39820494M&amp;a=006&amp;gp_orden=G3&amp;seg=32379c48c3ef5204d3cd211147edf70844ffa1d5&amp;posicion=11&id_busq=paol2086113324Xbfd08a715a94d0cc&site=paol&mode=SIMPLE&pext=\">web</a></li>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "    <li class=\"\"><a rel=\"nofollow\" id=\"t11\" class=\"telefono\" href='javascript:\n" +
            "\t\t\t\t\t\t\taddDiv(\"t\", \"11\");\n" +
            "\t\t\t\t\t\t\tcargarParametrosMoxqum(\"paol2086113324Xbfd08a715a94d0cc\",\"006\",\"39820494M\",\"G3\",\"11\",\"PAM\",\"IL\",\"\",\"\");\n" +
            "\t\t\t\t\t\t\tgetTel(\"11\",\"false\",\"1\",\"39820494M-006\",\"EXCURSIONES MAR&#xCD;TIMAS JOVEN ANA BEL&#xC9;N\",\"http://www.paginasamarillas.es/jump?dest=http%3A%2F%2Fwww.crucerosmarmenor.net&amp;t=IL&amp;producto=PAO&amp;c=39820494M&amp;a=006&amp;gp_orden=G3&amp;seg=32379c48c3ef5204d3cd211147edf70844ffa1d5&amp;posicion=11&id_busq=paol2086113324Xbfd08a715a94d0cc&site=paol&mode=SIMPLE&pext=\",\"http://www.paginasamarillas.es/fichas/excursiones-maritimas-joven-ana-belen_39820494M_006.html\",\"Ordenar por:\",\"Inicial\",\"Alfabético\",\"Selecciona una dirección para ver su teléfono\",\"Más información »\",5,\"\",\"\",true,\"IL\");'>\n" +
            "        teléfono\n" +
            "    </a>\n" +
            "    </li>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "    <li class=\"\"><a rel='nofollow' id='m11' class='vermapa' title=\"Se abre en una nueva ventana\" href='javascript:\n" +
            "\t\t\t\t\t\taddDiv(\"m\", \"11\");\n" +
            "\t\t\t\t\t\tcargarParametrosAditionalMoxqum(\"paol\",\"SIMPLE\");\n" +
            "\t\t\t\t\t\tcargarParametrosMoxqum(\"paol2086113324Xbfd08a715a94d0cc\",\"006\",\"39820494M\",\"G3\",\"11\",\"PAM\",\"IL\",\"\",\"\");\n" +
            "\t\t\t\t\t\tgetAddress(\"11\",\"false\",\"1\",\"39820494M-006\",\"EXCURSIONES MAR&#xCD;TIMAS JOVEN ANA BEL&#xC9;N\",\"http://www.paginasamarillas.es/jump?dest=http%3A%2F%2Fwww.crucerosmarmenor.net&amp;t=IL&amp;producto=PAO&amp;c=39820494M&amp;a=006&amp;gp_orden=G3&amp;seg=32379c48c3ef5204d3cd211147edf70844ffa1d5&amp;posicion=11&id_busq=paol2086113324Xbfd08a715a94d0cc&site=paol&mode=SIMPLE&pext=\",\"http://callejero.paginasamarillas.es\",\"/sites/home/mapdir.asp?\",\"/VEMaps/mapa.asp?\",\"http://www.paginasamarillas.es/jump_callejero\",\"Ordenar por:\",\"Inicial\",\"Alfabético\",\"Selecciona una dirección para ver su situación\",\"5\",\"\",\"\",true,\"IL\");'>\n" +
            "        ver mapa\n" +
            "    </a>\n" +
            "    </li>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "    <li class=\"\"><a rel=\"nofollow\" id=\"c11\" class=\"compartir\" href=\"javascript:\n" +
            "\t\t\t\t\taddDiv('c', '11');\n" +
            "\t\t\t\t\tcargarParametrosMoxqum('paol2086113324Xbfd08a715a94d0cc','006','39820494M','G3','11','PAM','IL','','');\n" +
            "\t\t\t\t\tgetCompartir('11','39820494M-006','MURCIA','http://www.paginasamarillas.es/fichas/excursiones-maritimas-joven-ana-belen_39820494M_006.html','http://www.paginasamarillas.es/jump?dest=','http%3A%2F%2Fwww%2Efacebook%2Ecom%2Fshare%2Ephp%3Fu%3D','http%3A%2F%2Ftwitthis%2Ecom%2Ftwit%3Furl%3D','http%3A%2F%2Fdel%2Eicio%2Eus%2Fpost%3Furl%3D','http%3A%2F%2Fwww%2Egoogle%2Ecom%2Fbookmarks%2Fmark%3Fop%3Dedit%26bkmk%3D','http%3A%2F%2Fbookmarks%2Eyahoo%2Ecom%2Ftoolbar%2Fsavebm%3Fopener%3Dtb%26u%3D','http://www.paginasamarillas.es','Comparte los datos de esta empresa con tu red social favorita','Más info.',true);\">\n" +
            "        compartir\n" +
            "    </a>\n" +
            "    </li>\n" +
            "\n" +
            "    <li class=\"\"><a class=\"notab PaolClick omn_fFavorito_11\"\n" +
            "                    rel=\"nofollow\" id=\"f11\" href=\"#favoritos\" onclick=\"javascript:\n" +
            "\t\t\t\tredirPaoLogon('http://www.paginasamarillas.es/favoritos?idInsercion=39820494M&productType=PAM&deActividad=DESPEDIDAS+DE+SOLTEROS&txTitulo=EXCURSIONES+MAR%C3%8DTIMAS+JOVEN+ANA+BEL%C3%89N&pgInsercion=006&pgInsDireccion=-1&idTpAnun=IL&t=IL&id_busq=paol2086113324Xbfd08a715a94d0cc&c=39820494M&a=006&cod_ret=0&prod=PAM&gp_orden=G3&site=paol','https://miarea.paginasamarillas.es/acceso-usuario?Referer=1',this.id);\">\n" +
            "        guardar en favoritos\n" +
            "    </a>\n" +
            "    </li>\n" +
            "</ul>\n" +
            "\n" +
            "<div class=\"footers\" style=\"display:none\" id=\"funcionalidad11\"></div>\n" +
            "</div>";
}
