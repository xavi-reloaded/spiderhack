package com.androidxtrem.spider.util;

import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 8/8/12
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaginasAmarillasJSTest {

//    http://www.paginasamarillas.es/dwr/call/plaincall/AddressSearchDWR_7_9_3.getAdvancedAddress.dwr
//    ?callCount=1&page=/search/pegamento/all-ma/all-pr/all-is/all-ci/all-ba/all-pu/all-nc/1?cu=pegamento&ub=false&httpSessionId=&scriptSessionId=303C696380BD3993C2B0E36654034385394&c0-scriptName=AddressSearchDWR_7_9_3&c0-methodName=getAdvancedAddress&c0-id=0&c0-param0=string:31151887N-006&c0-param1=string:&c0-param2=string:&batchId=15&&&&&&&

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

    private PaginasAmarillasJS sut;

    @BeforeMethod
    public void setUp() throws Exception {
        sut = new PaginasAmarillasJS();
    }


    @Test(enabled = false)
    public void test_getObjectFromString() throws Exception {

        String strFromJSService = "//#DWR-INSERT\n" +
                "//#DWR-REPLY\n" +
                "var s0={};var s1={};var s2={};var s3={};var s4={};var s5={};var s6={};var s7={};s0.cam=\"Avenida Mariano Ballester, s/n (Embarcadero del CAR - P\\u00BA Mar\\u00EDtimo de los Narejos)\";s0.fax=null;s0.latitude=\"37.751274\";s0.locality=\"LOS ALCAZARES\";s0.localityS=\"LOS NAREJOS\";s0.longitude=\"-0.838075\";s0.postalCode=\"30740\";s0.province=\"MURCIA\";s0.realAddressNumber=\"1\";s0.smsCode=\"M79L1\";s0.streetName=\"Mariano Ballester\";s0.streetType=\"Avenida\";s0.telephone=\"696440909\";s0.telephone2=null;s0.tg=\"P\";\n" +
                "s1.cam=null;s1.fax=null;s1.latitude=null;s1.locality=\"SAN PEDRO DEL PINATAR\";s1.localityS=\"LO PAGAN\";s1.longitude=null;s1.postalCode=null;s1.province=\"MURCIA\";s1.realAddressNumber=\"1\";s1.smsCode=\"M3CXX\";s1.streetName=null;s1.streetType=null;s1.telephone=\"696440909\";s1.telephone2=null;s1.tg=null;\n" +
                "s2.cam=null;s2.fax=null;s2.latitude=null;s2.locality=\"CARTAGENA\";s2.localityS=\"CABO DE PALOS\";s2.longitude=null;s2.postalCode=null;s2.province=\"MURCIA\";s2.realAddressNumber=\"1\";s2.smsCode=\"M3CX3\";s2.streetName=null;s2.streetType=null;s2.telephone=\"696440909\";s2.telephone2=null;s2.tg=null;\n" +
                "s3.cam=null;s3.fax=null;s3.latitude=null;s3.locality=\"SAN JAVIER\";s3.localityS=\"SANTIAGO DE LA RIBERA\";s3.longitude=null;s3.postalCode=null;s3.province=\"MURCIA\";s3.realAddressNumber=\"1\";s3.smsCode=\"M3CXT\";s3.streetName=null;s3.streetType=null;s3.telephone=\"696440909\";s3.telephone2=null;s3.tg=null;\n" +
                "s4.cam=null;s4.fax=null;s4.latitude=null;s4.locality=\"CARTAGENA\";s4.localityS=\"LA MANGA DEL MAR MENOR\";s4.longitude=null;s4.postalCode=null;s4.province=\"MURCIA\";s4.realAddressNumber=\"1\";s4.smsCode=\"M3CXP\";s4.streetName=null;s4.streetType=null;s4.telephone=\"696440909\";s4.telephone2=null;s4.tg=null;\n" +
                "s5.cam=null;s5.fax=null;s5.latitude=null;s5.locality=\"CARTAGENA\";s5.localityS=\"LOS URRUTIAS\";s5.longitude=null;s5.postalCode=null;s5.province=\"MURCIA\";s5.realAddressNumber=\"1\";s5.smsCode=\"M3CX7\";s5.streetName=null;s5.streetType=null;s5.telephone=\"696440909\";s5.telephone2=null;s5.tg=null;\n" +
                "s6.cam=null;s6.fax=null;s6.latitude=null;s6.locality=\"MURCIA\";s6.localityS=\"MURCIA\";s6.longitude=null;s6.postalCode=null;s6.province=\"MURCIA\";s6.realAddressNumber=\"1\";s6.smsCode=\"M3CX8\";s6.streetName=null;s6.streetType=null;s6.telephone=\"696440909\";s6.telephone2=null;s6.tg=null;\n" +
                "s7.cam=null;s7.fax=null;s7.latitude=null;s7.locality=\"MAZARRON\";s7.localityS=\"MAZARRON\";s7.longitude=null;s7.postalCode=null;s7.province=\"MURCIA\";s7.realAddressNumber=\"1\";s7.smsCode=\"M3CXD\";s7.streetName=null;s7.streetType=null;s7.telephone=\"696440909\";s7.telephone2=null;s7.tg=null;\n" +
                "dwr.engine._remoteHandleCallback('0','0',[s0,s1,s2,s3,s4,s5,s6,s7]";

        List<PAResponseObject> paResponseObject= sut.getPAResponseObjectListFromString(strFromJSService);

        Assert.assertEquals(123,paResponseObject.size());

    }
}
