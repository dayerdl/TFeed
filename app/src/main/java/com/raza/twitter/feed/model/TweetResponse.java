package com.raza.twitter.feed.model;

import java.util.List;

/**
 * Created by jaffarraza on 19/08/16.
 */
public class TweetResponse {

    public List<TweetItem> getTweetItems() {
        return tweetItems;
    }

    public void setTweetItems(List<TweetItem> tweetItems) {
        this.tweetItems = tweetItems;
    }

    List<TweetItem> tweetItems;
}
