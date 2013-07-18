package com.androidxtrem.spider.util;

import com.androidxtrem.spider.domain.DataFax;
import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 7/30/12
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpiderGettedFileTest {

    private SpiderGettedFile sut;

    @BeforeMethod
    public void setUp() throws Exception {
        sut = new SpiderGettedFile();
    }

    @Test(enabled = false)
    public void test_getDataFromGettedFile() throws Exception {
        File sample = new File(SpiderGettedFileTest.class.getResource("/sample.txt").getPath());
        DataFax dataFax = sut.getDataFromGettedFile(sample);
        Assert.assertEquals(dataFax.getUrl(),"http://deporte.ugr.es/pages/biblioteca/index",dataFax.getUrl());
        Assert.assertEquals(dataFax.getFax(),"958249425",dataFax.getFax());
    }

    @Test(enabled = false)
    public void test_getDataFromGettedFile_badFaxNumber() throws Exception {
        File sample = new File(SpiderGettedFileTest.class.getResource("/sample_bad_number.txt").getPath());
        DataFax dataFax = sut.getDataFromGettedFile(sample);
        Assert.assertEquals(dataFax.getUrl(),"http://deporte.ugr.es/pages/biblioteca/index",dataFax.getUrl());
        Assert.assertEquals(dataFax.getFax(),"958249425",dataFax.getFax());
    }

    @DataProvider (name="faxes")
    Object[][] getFaxes(){
        return new Object[][]{
                {"(+34) 93 437 16 28"},
                {"+34 93 437 16 28"},
                {"fax[(+34) 93 437 16 28]"},
                {"fax[(+34) 93 437 16 28]"},
                {"9343716280"},
                {"934371628"},
        };
    }

    @Test(dataProvider = "faxes")
    public void test_parseFaxNumber_(String notParsedFax) throws Exception {
        String actual = sut.parseFaxNumber(notParsedFax);
        Assert.assertEquals("ERROR: ["+actual+"]","+34934371628",actual);
    }


}
