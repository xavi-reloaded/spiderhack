package com.androidxtrem.spider.parser;

import com.androidxtrem.spider.StringHelper;
import com.androidxtrem.spider.domain.DataFicha;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 8/6/12
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaginasAmarillasFichaParser extends BaseParserHtmlUtil{

    private static final String TITLE_EXPRESSION = "microficha.*.\\n+\\s+<a\\n+\\s+.*\\n+\\s+.*\\n+\\s+.*\\n+\\s+>(.+?)<";
    private static final String ADDRESS_EXPRESSION = "adr.*\\n+\\s+(.+?)\\n";

    private static final String POSTALCODE_EXPRESSION = "postal-code\">(.+?)<";
    private static final String LOCALITY_EXPRESSION = "locality\">(.+?)<";
    private static final String REGION_EXPRESSION = "region\">(.+?)<";

    private static final String INSIDETAG_EXPRESSION = "\">(.+?)</";
    private static final String PARAMS_EXPRESSION = "cargarParametrosMoxqum\\((.+)\\)";


    private DataFicha dataFicha;

    public PaginasAmarillasFichaParser() {
        dataFicha = new DataFicha();
    }

    @Override
    public String parseTxtToHTml(String txtDefinition, String language) {
        dataFicha.setTitle(getTitleFromPAHtml(txtDefinition));
        return null;
    }

    public String getTitleFromPAHtml(String html) {
        return getRegexpGroup(html,Pattern.compile(TITLE_EXPRESSION));
    }

    public String getAddressFromPAHtml(String html) {
        return getRegexpGroup(html,Pattern.compile(ADDRESS_EXPRESSION));
    }

    public String getPostalCodeFromPAHtml(String html) {
        return getRegexpGroup(html,Pattern.compile("postal-code"+INSIDETAG_EXPRESSION));
    }

    public String getLocalityFromPAHtml(String html) {
        return getRegexpGroup(html,Pattern.compile("locality"+INSIDETAG_EXPRESSION));
    }

    public String getRegionFromPAHtml(String html) {
        return getRegexpGroup(html,Pattern.compile("region"+INSIDETAG_EXPRESSION));
    }

    public String getEnterpriseDataFromPAHtml(String html) {
        String data =  getRegexpGroup(html,Pattern.compile("caja\">.+?</h2>(.+?)</div>", Pattern.DOTALL | Pattern.MULTILINE));
        data = StringHelper.cleanHtmlText(data);
        return data;
    }


    public String getParamsFromPAHtml(String html) {
        return getRegexpGroup(html,Pattern.compile(PARAMS_EXPRESSION));
    }

    public String getPhoneDataFromPAHtml(String html) {
        String params = getRegexpGroup(html,Pattern.compile(PARAMS_EXPRESSION));
        String enterpriseId = getEnterpriseIdFromParamArguments(params);
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private String getRegexpGroup(String txtDefinition,Pattern pattern) {
        Matcher m = pattern.matcher(txtDefinition);
        if (m.find()) { return m.group(1); }
        return "";
    }

    public String getEnterpriseIdFromParamArguments(String arguments) {
        String[] str = arguments.replace("\"","").split(",");
        return str[2]+"-"+str[1];
    }
}
