package com.raza.twitter.feed.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jaffarraza on 19/08/16.
 */
public class HashtagEntity extends Entity {

    /**
     * Name of the hashtag, minus the leading '#' character.
     */
    @SerializedName("text")
    public final String text;

    public HashtagEntity(String text, int start, int end) {
        super(start, end);
        this.text = text;
    }
}