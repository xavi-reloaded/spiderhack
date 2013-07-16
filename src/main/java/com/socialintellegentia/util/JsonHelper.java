package com.socialintellegentia.util;

import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sidev
 * Date: 7/15/13
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonHelper {
    public static List<String> getRssSourcesFromJson(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<String> rssSources = new ArrayList<String>();
        for ( int x=0;x< jsonArray.length();x++) {
            JSONObject jsonObject = jsonArray.getJSONObject(x);
            rssSources.add(jsonObject.getString("source"));
        }
        return rssSources;
    }
}
