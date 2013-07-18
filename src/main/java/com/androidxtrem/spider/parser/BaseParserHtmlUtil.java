package com.androidxtrem.spider.parser;

import org.apache.commons.lang.NullArgumentException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class BaseParserHtmlUtil implements IParserHtmlUtil {

    public String matchAndReplace(String text, Pattern pattern, String replacement)
    {
        if (pattern == null) {
            throw new NullArgumentException("pattern");
        }
        if (text=="") return "";
        Matcher newMatcher=pattern.matcher(text);
        if (!newMatcher.find()) return text;
        Matcher matcher = pattern.matcher(text);
        String currentReplacement;
        StringBuffer sb=new StringBuffer();
        String unchangedSubstring=text;
        while(matcher.find())
        {
            currentReplacement=cleanReplacement(unchangedSubstring, pattern, replacement);
            matcher.appendReplacement(sb, currentReplacement);
            unchangedSubstring=text.substring(matcher.toMatchResult().end());
        }
        sb.append(unchangedSubstring);
        return sb.toString();
    }

    private String cleanReplacement(String text, Pattern pattern, String replacement)
    {
        Matcher matcher=pattern.matcher(text);
        if(!matcher.find()) return replacement;
        for(int i=0; i<matcher.groupCount(); ++i)
        {
            if(matcher.group(i)==null)
            {
                replacement=replacement.replaceAll("\\$"+i, "");
            }
        }
        return replacement;
    }

    @Override
    public abstract String parseTxtToHTml(String txtDefinition, String language);

    public boolean isCategoryLine(String catGramLine, String[] grammaticalList)
    {
        final String cg = catGramLine.toLowerCase().trim();
        for (String cat : grammaticalList) {
            final String trim = cat.toLowerCase().trim();
            if (trim.equals(cg)) return true;
        }
        return false;
    }
}
