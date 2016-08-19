package com.raza.twitter.feed.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/**
 * Created by jaffarraza on 19/08/16.
 */
public class UserEntities {

    @SerializedName("url")
    public final UrlEntities url;

    @SerializedName("description")
    public final UrlEntities description;

    public UserEntities(UrlEntities url, UrlEntities description) {
        this.url = url;
        this.description = description;
    }

    public static class UrlEntities {

        @SerializedName("urls")
        public final List<UrlEntity> urls;

        public UrlEntities(List<UrlEntity> urls) {
            this.urls = getSafeList(urls);
        }

        private <T> List<T> getSafeList(List<T> entities) {
            // Entities may be null if Gson does not find object to parse. When that happens, make
            // sure to return an empty list.
            if (entities == null) {
                return Collections.EMPTY_LIST;
            } else {
                return Collections.unmodifiableList(entities);
            }
        }
    }
}

