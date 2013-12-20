package com.socialintellegentia.util;

import com.androidxtrem.nlp.contents.StandardContentKyoto;

import java.io.IOException;

/**
 * Created by xavi on 12/20/13.
 */
public class UniqueFeedHandler implements IUniqueFeedHandler{

    private static UniqueFeedHandler instance = null;
    private static final String GUI_HASH_PATH = "/usr/local/share/kyotodata/metadata/guiPersistentHash.kch";
    private static StandardContentKyoto guiPersistentHash;


    public UniqueFeedHandler(){

    }

    public static UniqueFeedHandler getInstance() {
        if(instance == null) {
            instance = new UniqueFeedHandler();
            try {
                guiPersistentHash = new StandardContentKyoto(GUI_HASH_PATH,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }


    @Override
    public void put(String feedGui) {
        guiPersistentHash.put(feedGui,"1");
    }

    @Override
    public boolean exists(String feedGui) {
        String result = null;
        boolean exists = false;
        try {
            result = guiPersistentHash.get(feedGui);
            if (result!=null) exists = result.equals("1");
        } catch (IOException e) {
            return false;
        }
        return exists;
    }

    public void remove(String feedGui) {
        guiPersistentHash.put(feedGui,"0");
    }
}
