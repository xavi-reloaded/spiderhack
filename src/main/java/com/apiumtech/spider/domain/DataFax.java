package com.apiumtech.spider.domain;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 7/30/12
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataFax {
    private String fax;
    private String url;

    public String getFax() {
        return fax;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setFax(String fax) {
        this.fax = fax;
    }

    @Override
    public String toString() {
        return getUrl() + "^" + getFax();
    }
}
