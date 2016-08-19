package com.raza.twitter.feed.view.decorator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.raza.twitter.feed.R;
import com.raza.twitter.feed.bLogic.ImageUtils;
import com.raza.twitter.feed.model.TweetItem;

import java.util.List;

/**
 * Created by jaffarraza on 18/08/16.
 */
public class TweetItemAdapter extends ArrayAdapter<TweetItem> {
    private List<TweetItem> tweets;
    private Context context;

    public TweetItemAdapter(Context context, int textViewResourceId, List<TweetItem> tweets) {
        super(context, textViewResourceId, tweets);
        this.tweets = tweets;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.tweet_item, null);
        }
        TweetItem tweet = tweets.get(position);
        if (tweet != null) {
            TextView username = (TextView) v.findViewById(R.id.username);
            TextView message = (TextView) v.findViewById(R.id.message);
            ImageView image = (ImageView) v.findViewById(R.id.avatar);
            if (username != null) {
                username.setText(tweet.user.name);
            }
            if (message != null) {
                message.setText(tweet.text);
            }
            if (image != null) {
                ImageUtils.loadImage(context, tweet.user.profileImageUrl, image);
            }
        }
        return v;
    }
}