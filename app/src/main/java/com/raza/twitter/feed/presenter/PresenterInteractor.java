package com.raza.twitter.feed.presenter;

/**
 * Created by jaffarraza on 18/08/16.
 */
public interface PresenterInteractor {
    void loadUserTimeLine(String token, String s);
    void searchFeedItems(String token, String s, String searchItem);
    void rxUnSubscribe();
}
