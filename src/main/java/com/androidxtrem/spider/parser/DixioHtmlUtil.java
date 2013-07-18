package com.androidxtrem.spider.parser;


import java.util.regex.Pattern;

public class DixioHtmlUtil extends BaseParserHtmlUtil {

    private static final String ENTRY_EXPRESSION = "(^[^\n]*)\\n";
    private static final String ENTRY_REPLACEMENT = "<p class='giclema'><span class='gicfn_1'><span class='giccl_navy'><b>$1</b></span></span></p>\n";

    private static final String ACCEPTION_EXPRESSION = "(\\d+.?\\s)([^\n\\|]*)(\\|\\||\n\\|\\||\n|)?$?";
    private static final String ACCEPTION_REPLACEMENT = "<p class='gicdict'><span class='giccl_navy'><b>$1</b></span> $2</p>$3";


    private static final String POS_EXPRESSION = "\\n(\\|\\|)?([^\\d].*?)\\n";
    private static final String POS_REPLACEMENT = "\n$1<p class='giclema'><span class='gicgrm'><span class='giccl_maroon'><b><abbr><span class='gichint'>$2 </span></abbr> </b></span></span></p>\n";

    private static final String REGISTRY_EXPRESSION = "\\[(.*?)\\]";
    private static final String REGISTRY_REPLACEMENT = "<span class='giccl_green'><i>[$1] </i></span>";

    private static final String DOUBLEPIPE_EXPRESSION = "\\|\\|";
    private static final String DOUBLEPIPE_REPLACEMENT = "<hr/>";

    private Pattern entryPattern;
    private Pattern acceptionPattern;
    private Pattern posPattern;
    private Pattern registryPattern;
    private Pattern doublePipePattern;

    public DixioHtmlUtil() {
        this.entryPattern = Pattern.compile(ENTRY_EXPRESSION);
        this.acceptionPattern = Pattern.compile(ACCEPTION_EXPRESSION);
        this.posPattern = Pattern.compile(POS_EXPRESSION);
        this.registryPattern = Pattern.compile(REGISTRY_EXPRESSION);
        this.doublePipePattern = Pattern.compile(DOUBLEPIPE_EXPRESSION);
    }

    public String replaceEntry(String txtDefinition) {
        return matchAndReplace(txtDefinition, this.entryPattern, ENTRY_REPLACEMENT);
    }

    public String replaceAception(String txtDefinition) {
        return matchAndReplace(txtDefinition, this.acceptionPattern, ACCEPTION_REPLACEMENT);
    }

    public String replacePos(String txtDefinition) {
        return matchAndReplace(txtDefinition, this.posPattern, POS_REPLACEMENT);
    }

    public String replaceRegistry(String txtDefinition) {
        return matchAndReplace(txtDefinition, this.registryPattern, REGISTRY_REPLACEMENT);
    }
    public String replaceDoublePipe(String txtDefinition){
        return matchAndReplace(txtDefinition, this.doublePipePattern, DOUBLEPIPE_REPLACEMENT);
    }

    @Override
    public String parseTxtToHTml(String txtDefinition, String language) {

        txtDefinition = txtDefinition.replaceAll("\n\n", "\n");

        txtDefinition = patchAcceptionWithoutNumbers(txtDefinition);

        //System.out.println(txtDefinition);

        txtDefinition = txtDefinition.replaceAll("\t", "");
        txtDefinition = txtDefinition.replaceAll("\n\\|\\|", "\\|\\|\n");
        txtDefinition = replaceEntry(txtDefinition);
        txtDefinition = replacePos(txtDefinition);
        txtDefinition = replaceAception(txtDefinition);
        txtDefinition = replaceRegistry(txtDefinition);
        txtDefinition = replaceDoublePipe(txtDefinition);

        return txtDefinition.replaceAll("\n", "");
    }

    private String patchAcceptionWithoutNumbers(String txtDefinition)
    {
        //System.out.println("[patchAcceptionWithoutNumbers] starts");

        if (txtDefinition.length()==0) return txtDefinition;
        if (!txtDefinition.contains("||")) return patchAcceptionWithoutNumberParse(txtDefinition, true);

        String[] defs = txtDefinition.split("\\|\\|");
        //System.out.println("[patchAcceptionWithoutNumbers] split[" + defs.length + "] ||");
        StringBuilder builder = new StringBuilder();
        Integer count=1;
        String lema="";
        for (String def:defs) {
            //System.out.println("[patchAcceptionWithoutNumbers] def: <" + def + ">");

            if ("".equals(def))
            {
                //System.out.println("[patchAcceptionWithoutNumbers] def1: <" + def + ">");
                continue;
            }
            if (count==1)
            {
                //System.out.println("[patchAcceptionWithoutNumbers] def2: <" + def + ">");
                lema = (def.contains("\n")) ? def.split("\\n")[0] : "";
                //System.out.println("[patchAcceptionWithoutNumbers] def2: obtein lema:<" + def + "> ");
            }
            else
            {
                //System.out.println("[patchAcceptionWithoutNumbers] def3: <" + def + ">");
                def = new StringBuilder(lema).append("\n").append(def).toString();
            }
            String str = patchAcceptionWithoutNumberParse(def,(count==1));
            //System.out.println("[patchAcceptionWithoutNumbers] appended: <" + str + ">");
            builder.append(str);

            if (count==defs.length) builder.append("||");

            count++;


        }
        String result=builder.toString().replace("||||","||");

        String lemaBis = new StringBuilder(lema).append("\n").toString();

        if (countMatches(txtDefinition, lemaBis) < countMatches(result, lemaBis))
        {
            return txtDefinition;
        }

        //System.out.println("[patchAcceptionWithoutNumbers] ends: <" + result + ">");
        return result;

    }

    private Integer countMatches(String txtDefinition, String lemaBis)
    {

        return txtDefinition.split(lemaBis).length;
    }

    private String patchAcceptionWithoutNumberParse(String txtDefinition, boolean isFirst)
    {
        if (txtDefinition.length()==0) return txtDefinition;
        String split[] = txtDefinition.split("\\n");
        StringBuilder result=new StringBuilder();
        int y = 0;
        if( split.length == 1 )
        {
            return txtDefinition;
        }
        else
        {
            if (split[0].equals("")||split[1].equals(""))
            {
                if (!isFirst) return txtDefinition.substring(txtDefinition.indexOf("\n"));
                return txtDefinition;
            }

            if (split.length > 2 && split[1].trim().endsWith(".")) {
               if (split[2].equals(""))
               {
                   if (!isFirst) return txtDefinition.substring(txtDefinition.indexOf("\n"));
                   return txtDefinition;
               }
               if (Character.isDigit(split[2].trim().charAt(0))) return txtDefinition;
               result.append(split[0]).append("\n").append(split[1]).append("\n").append("1 ").append(split[2]);
               y=3;

            } else if (split.length > 1) {
               if (Character.isDigit(split[1].trim().charAt(0))) return txtDefinition;
               result.append(split[0]).append("\n").append("1 ").append(split[1]);
               y=2;

            }
            int xx=2;
            for (int x=y;x<split.length;x++) {
                result.append("\n");
                if (!Character.isDigit(split[x].trim().charAt(0))) {
                    if (!isPre(split[x]) ) {
                        result.append(xx + " ");
                        xx++;
                    } else {
                        xx=1;
                    }
                }
                result.append(split[x]);
            }

        }

        if (!result.toString().endsWith("\n||")) result.append("\n").append("||");

        if (!isFirst) return result.toString().substring(result.toString().indexOf("\n"));

        return result.toString();
    }

    private boolean isPre(String str)
    {
        str=str.trim();
        if (str.contains("&")) str=str.substring(0,str.indexOf("&")).trim();
        if (str.contains("[")) str=str.substring(0,str.indexOf("[")).trim();
        if (str.contains("(")) str=str.substring(0,str.indexOf("(")).trim();
        if (str.endsWith(".") && str.length()<=6) return true;

        return false;
    }


}
