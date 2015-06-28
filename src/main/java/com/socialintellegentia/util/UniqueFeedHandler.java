package com.socialintellegentia.util;


import java.io.IOException;

/**
 * Created by xavi on 12/20/13.
 */
public class UniqueFeedHandler implements IUniqueFeedHandler{

    private static UniqueFeedHandler instance = null;
    private static final String GUI_HASH_PATH = "/usr/local/share/kyotodata/metadata/guiPersistentHash.kch";
//    private static StandardContentKyoto guiPersistentHash;


    public UniqueFeedHandler(){

    }

    public static UniqueFeedHandler getInstance() {
        if(instance == null) {
            instance = new UniqueFeedHandler();
//            try {
////                guiPersistentHash = new StandardContentKyoto(GUI_HASH_PATH,true);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        return instance;
    }


    @Override
    public void put(String feedGui) {

    }

    @Override
    public boolean exists(String feedGui) {
        boolean exists = false;
            String result = "0"; //guiPersistentHash.get(feedGui);
            if (result!=null) exists = result.equals("1");

        return exists;
    }

}
