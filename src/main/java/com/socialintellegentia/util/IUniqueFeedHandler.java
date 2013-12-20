package com.socialintellegentia.util;

/**
 * Created by xavi on 12/20/13.
 */
public interface IUniqueFeedHandler {
    void put(String feedGui);
    boolean exists(String feedGui);
}
