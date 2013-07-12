package com.apiumtech.spider.parser;

import com.apiumtech.spider.StringHelper;
import com.apiumtech.spider.domain.DataFicha;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 8/6/12
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class SeccionAmarillaMeFichaParser extends BaseParserHtmlUtil{

    private static final String INSIDETAG_EXPRESSION = "\">(.+?)</";
    private static final String PHONE_EXPRESSION = "phone\">\\n(.*)</div>";

    private DataFicha dataFicha;

    public SeccionAmarillaMeFichaParser() {
        dataFicha = new DataFicha();
    }

    @Override
    public String parseTxtToHTml(String txtDefinition, String language) {
        dataFicha.setTitle(getTitleFromPAHtml(txtDefinition));
        return null;
    }

    public String getTitleFromPAHtml(String html) {
        return getRegexpGroup(html,Pattern.compile("listado_destacado"+INSIDETAG_EXPRESSION));
    }

    public String getAddressFromPAHtml(String html) {
        return getRegexpGroup(html,Pattern.compile("street-address"+INSIDETAG_EXPRESSION));
    }

    public String getPostalCodeFromPAHtml(String html) {
        return getRegexpGroup(html,Pattern.compile("postal-code"+INSIDETAG_EXPRESSION));
    }

    public String getLocalityFromPAHtml(String html) {
        return getRegexpGroup(html,Pattern.compile("locality"+INSIDETAG_EXPRESSION));
    }

    public String getCategoriaFromPAHtml(String html) {
        String catego = getRegexpGroup(html, Pattern.compile("catego" + INSIDETAG_EXPRESSION));
        catego = getRegexpGroup(catego, Pattern.compile(">(.*)"));
        return catego;
    }
    public String getPhoneFromPAHtml(String html) {
        return getRegexpGroup(html,Pattern.compile(PHONE_EXPRESSION)).trim();
    }

    private String getRegexpGroup(String txtDefinition,Pattern pattern) {
        Matcher m = pattern.matcher(txtDefinition);
        if (m.find()) { return m.group(1); }
        return "";
    }
}
