package com.raza.twitter.feed.network;

/**
 * Created by jaffarraza on 18/08/16.
 */

import android.os.AsyncTask;

import com.raza.twitter.feed.prefs.Constants;
import com.raza.twitter.feed.presenter.ViewInterface;
import com.raza.twitter.feed.view.activities.Login;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
/**
 * Created by jaffarraza on 18/08/16.
 */
public class GetToken extends AsyncTask<String, String, RequestToken> {

    private final Login view;

    public GetToken(Login view) {
        this.view = view;
    }

    @Override
    protected RequestToken doInBackground(String... args) {
        try {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(Constants.TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(Constants.TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();

            TwitterFactory factory = new TwitterFactory(configuration);
            Twitter twitter = factory.getInstance();

            RequestToken requestToken = twitter.getOAuthRequestToken();
            return requestToken;
        } catch (TwitterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(RequestToken reqToken) {
        if (reqToken == null) {
            view.showFailure(ViewInterface.ErrorType.RESPONSE, null);
        } else {
            view.showRessults(reqToken);
        }
    }
}