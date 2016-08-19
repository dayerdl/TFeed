package com.raza.twitter.feed.view.application;

import android.app.Application;

import com.raza.twitter.feed.network.NetworkService;

/**
 * Created by jaffarraza on 18/08/16.
 */
public class TweetApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "20pjwO5RvvrTxbqbAyNA7W5uy";
    private static final String TWITTER_SECRET = "iQqCxf4tiiARONNxJiIedQc6QqD9p5wrW3raIrnHQKfBUlcAZb";


    private NetworkService networkService;

    @Override
    public void onCreate() {
        super.onCreate();

        networkService = new NetworkService();

    }

    public NetworkService getNetworkService(){
        return networkService;
    }


}
