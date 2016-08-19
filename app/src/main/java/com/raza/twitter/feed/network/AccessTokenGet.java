package com.raza.twitter.feed.network;

import android.os.AsyncTask;

import com.raza.twitter.feed.prefs.Constants;
import com.raza.twitter.feed.presenter.ViewInterface;
import com.raza.twitter.feed.view.activities.Login;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by jaffarraza on 18/08/16.
 */
public class AccessTokenGet extends AsyncTask<String, String, AccessToken> {

    private final RequestToken requestToken;
    private final Login view;

    public AccessTokenGet(Login view, RequestToken requestToken) {
        this.requestToken = requestToken;
        this.view = view;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected AccessToken doInBackground(String... args) {

        try {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(Constants.TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(Constants.TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();
            
            TwitterFactory factory = new TwitterFactory(configuration);
            Twitter twitter = factory.getInstance();

            AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, args[0]);
            return accessToken;


        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onPostExecute(AccessToken response) {
        if (response == null) {
            view.showFailure(ViewInterface.ErrorType.RESPONSE, null);
        } else {
            view.showRessults(response);
        }
    }


}