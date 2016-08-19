package com.raza.twitter.feed.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

/**
 * Created by jaffarraza on 18/08/16.
 */
public class Entities {

    @SerializedName("hashtags")
    @Expose
    private List<Object> hashtags = new ArrayList<Object>();
    @SerializedName("symbols")
    @Expose
    private List<Object> symbols = new ArrayList<Object>();
    @SerializedName("user_mentions")
    @Expose
    private List<UserMention> userMentions = new ArrayList<UserMention>();
    @SerializedName("urls")
    @Expose
    private List<Url> urls = new ArrayList<Url>();

    /**
     *
     * @return
     * The hashtags
     */
    public List<Object> getHashtags() {
        return hashtags;
    }

    /**
     *
     * @param hashtags
     * The hashtags
     */
    public void setHashtags(List<Object> hashtags) {
        this.hashtags = hashtags;
    }

    /**
     *
     * @return
     * The symbols
     */
    public List<Object> getSymbols() {
        return symbols;
    }

    /**
     *
     * @param symbols
     * The symbols
     */
    public void setSymbols(List<Object> symbols) {
        this.symbols = symbols;
    }

    /**
     *
     * @return
     * The userMentions
     */
    public List<UserMention> getUserMentions() {
        return userMentions;
    }

    /**
     *
     * @param userMentions
     * The user_mentions
     */
    public void setUserMentions(List<UserMention> userMentions) {
        this.userMentions = userMentions;
    }

    /**
     *
     * @return
     * The urls
     */
    public List<Url> getUrls() {
        return urls;
    }

    /**
     *
     * @param urls
     * The urls
     */
    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

}
