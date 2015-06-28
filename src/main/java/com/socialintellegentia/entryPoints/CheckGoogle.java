package com.socialintellegentia.entryPoints;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.DummySpider;
import com.androidxtrem.spider.SpiderGoogleByDate;
import com.androidxtrem.spider.SpiderWasshotFeed;
import com.rometools.rome.io.FeedException;
import com.socialintellegentia.util.JsonHelper;
import org.json.JSONException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jonny
 * Date: 12/07/13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class CheckGoogle {

    public static void main(String[] args) {

        DummySpider spider = null;
        try {
            spider = new DummySpider();
            spider.methodainen();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
