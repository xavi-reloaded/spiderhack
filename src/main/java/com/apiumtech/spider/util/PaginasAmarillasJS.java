package com.apiumtech.spider.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 8/8/12
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaginasAmarillasJS {
    private String paginasAmarillasPhoneUrl = "http://www.paginasamarillas.es/dwr/call/plaincall/AddressSearchDWR_7_9_3.getAdvancedAddress.dwr";

    //?callCount=1&page=/search/pegamento/all-ma/all-pr/all-is/all-ci/all-ba/all-pu/all-nc/1?cu=pegamento&ub=false&httpSessionId=&scriptSessionId=303C696380BD3993C2B0E36654034385394&c0-scriptName=AddressSearchDWR_7_9_3&c0-methodName=getAdvancedAddress&c0-id=0&c0-param0=string:31151887N-006&c0-param1=string:&c0-param2=string:&batchId=15&&&&&&&

//    callCount=1
//    page=/search/pegamento/all-ma/all-pr/all-is/all-ci/all-ba/all-pu/all-nc/1?cu=pegamento&ub=false
//    httpSessionId=
//    scriptSessionId=303C696380BD3993C2B0E36654034385394
//    c0-scriptName=AddressSearchDWR_7_9_3
//    c0-methodName=getAdvancedAddress
//    c0-id=0
//    c0-param0=string:31151887N-006
//    c0-param1=string:
//    c0-param2=string:
//    batchId=15


    public String getData(String enterpriseId) throws IOException {
        //To change body of created methods use File | Settings | File Templates.

        return sendReq(paginasAmarillasPhoneUrl,enterpriseId);
    }

    public String sendReq(String url, String enterpriseId) throws IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);

        postMethod.addParameter("callCount", "1");
//        postMethod.addParameter("page", "page=/search/pegamento/all-ma/all-pr/all-is/all-ci/all-ba/all-pu/all-nc/1?cu=pegamento&ub=false");
//        postMethod.addParameter("httpSessionId", "");
        postMethod.addParameter("scriptSessionId", "");
        postMethod.addParameter("c0-scriptName", "AddressSearchDWR_7_9_3");
        postMethod.addParameter("c0-methodName", "getAdvancedAddress");
        postMethod.addParameter("c0-id", "0");
        postMethod.addParameter("c0-param0", "string:"+enterpriseId);
        postMethod.addParameter("c0-param1", "string:");
        postMethod.addParameter("c0-param2", "string:");
        postMethod.addParameter("batchId", "0");

        try {
            httpClient.executeMethod(postMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String resp = "";

        if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
            resp = postMethod.getResponseBodyAsString();
        } else {
            //...postMethod.getStatusLine();
        }
        return resp;
    }

    public List<PAResponseObject> getPAResponseObjectListFromString(String strFromJSService) {
        List<PAResponseObject> paResponseObjectList = new ArrayList<PAResponseObject>();

        String[] lines = strFromJSService.split("\n");

        for (String line : lines ){

                PAResponseObject paResponseObject = new PAResponseObject();






                System.out.println(line);


        }

        return paResponseObjectList;
    }
}
