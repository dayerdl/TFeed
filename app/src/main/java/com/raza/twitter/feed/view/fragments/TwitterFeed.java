package com.raza.twitter.feed.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.raza.twitter.feed.R;
import com.raza.twitter.feed.model.TweetItem;
import com.raza.twitter.feed.network.NetworkService;
import com.raza.twitter.feed.prefs.Constants;
import com.raza.twitter.feed.prefs.InstructionPreferences;
import com.raza.twitter.feed.presenter.PresenterInteractor;
import com.raza.twitter.feed.presenter.TweetsPresenter;
import com.raza.twitter.feed.presenter.ViewInterface;
import com.raza.twitter.feed.view.application.TweetApplication;
import com.raza.twitter.feed.view.decorator.TweetItemAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jaffarraza on 05/08/16.
 */
public class TwitterFeed extends BaseFragment implements ViewInterface {

    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.error)
    TextView errResp;

    private int page;
    private String label;
    private String token = "";
    private String tokenSecret = "";

    private NetworkService service;
    private PresenterInteractor presenter;

    List<TweetItem> firstItemsList;
    List<TweetItem> secondItemsList;


    // newInstance constructor for creating fragment with arguments
    public static TwitterFeed newInstance(int page, String label) {
        TwitterFeed feedFragment = new TwitterFeed();
        Bundle args = new Bundle();
        args.putInt(Constants.PAGE_INDEX, page);
        args.putString("label", label);
        feedFragment.setArguments(args);
        return feedFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_timeline;
    }

    @Override
    public void initViews(View parent, Bundle savedInstanceState) {
        super.initViews(parent, savedInstanceState);
        ButterKnife.bind(this, parent);

        page = getArguments().getInt(Constants.PAGE_INDEX, 0);
        label = getArguments().getString("label", "");

        service = ((TweetApplication) getActivity().getApplication()).getNetworkService();
        presenter = new TweetsPresenter(this, service);

        token = InstructionPreferences.readString(getActivity(), InstructionPreferences.OAUTH_TOKEN, "");
        tokenSecret = InstructionPreferences.readString(getActivity(), InstructionPreferences.OAUTH_TOKEN_SECRET, "");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (page == 1) {
            if (firstItemsList == null || firstItemsList.size() == 0) {
                presenter.searchFeedItems(token, tokenSecret, label);
            } else {
                listView.setAdapter(null);
                listView.setAdapter(new TweetItemAdapter(getActivity(), R.layout.tweet_item, firstItemsList));
                errResp.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            return;
        }

        if (page == 2) {
            if (secondItemsList == null || secondItemsList.size() == 0) {
                presenter.searchFeedItems(token, tokenSecret, label);
            } else {
                listView.setAdapter(null);
                listView.setAdapter(new TweetItemAdapter(getActivity(), R.layout.tweet_item, secondItemsList));
                errResp.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            return;
        }
    }

    @Override
    public void showRessults(Object response) {
        if (!isAdded()) {
            return;
        }
        errResp.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        if (page == 1) {
            firstItemsList = (List<TweetItem>) response;
            listView.setAdapter(null);
            listView.setAdapter(new TweetItemAdapter(getActivity(), R.layout.tweet_item, firstItemsList));
            return;
        }

        if (page == 2) {
            secondItemsList = (List<TweetItem>) response;
            listView.setAdapter(null);
            listView.setAdapter(new TweetItemAdapter(getActivity(), R.layout.tweet_item, secondItemsList));
            return;
        }
    }

    @Override
    public void showFailure(ErrorType type, Throwable throwable) {
        Log.d("TAG", throwable.toString());
        errResp.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void showInProcess() {
        errResp.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }


//    @Override
//    public void initViews(View parent, Bundle savedInstanceState) {
//        super.initViews(parent, savedInstanceState);
//        ButterKnife.bind(this, parent);
//
//        page = getArguments().getInt(Constants.PAGE_INDEX, 0);
//        label = getArguments().getString("label", "");
//
//        //Load tweets for Search Label
//        SearchTimeline searchTimeline = new SearchTimeline.Builder()
//                .query(label)
//                .build();
//        TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity()).setTimeline(searchTimeline)
//                .build();
//        listView.setAdapter(adapter);
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}