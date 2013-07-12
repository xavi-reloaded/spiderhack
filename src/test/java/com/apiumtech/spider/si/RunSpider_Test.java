package com.apiumtech.spider.si;

import com.socialintellegentia.entryPoints.runSpider;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: jonny
 * Date: 12/07/13
 * Time: 18:25
 * To change this template use File | Settings | File Templates.
 */
public class RunSpider_Test {

    private runSpider sut;

    @Test
    public void test_runSpider_empty_validResponse_Test() throws Exception {
        sut.main();
    }


}
