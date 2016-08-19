package com.raza.twitter.feed.prefs;

/**
 * Created by jaffarraza on 16/08/16.
 */
public class Constants {
    public static final String PREFS_NAME = "TWITTER_FEED_PREFS";

    public static final int SUCCESS = 200;
    public static final int SUCCESS_CREATED = 201;
    public static final int SUCCESS_ENDED = 202;
    public static final int UNAUTHORIZE = 400;
    public static final int SERVER_ERROR = 500;

    public static final String PAGE_INDEX = "page_index";

    public static String TWITTER_CONSUMER_KEY = "20pjwO5RvvrTxbqbAyNA7W5uy";
    public static String TWITTER_CONSUMER_SECRET = "iQqCxf4tiiARONNxJiIedQc6QqD9p5wrW3raIrnHQKfBUlcAZb";

    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

    static final String TWITTER_CALLBACK_URL = "http://jaffarraza.branded.me/";

    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "https://api.twitter.com/oauth/authorize";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "https://api.twitter.com/oauth/access_token";





}
