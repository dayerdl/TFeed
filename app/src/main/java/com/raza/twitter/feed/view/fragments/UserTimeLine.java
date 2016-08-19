package com.raza.twitter.feed.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class UserTimeLine extends BaseFragment implements ViewInterface {

    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.error)
    TextView errResp;

    private NetworkService service;
    private PresenterInteractor presenter;
    private String token;
    private String tokenSecret;
    private List<TweetItem> userTimeLineList;


    // newInstance constructor for creating fragment with arguments
    public static UserTimeLine newInstance(int page) {
        UserTimeLine timeLineFragment = new UserTimeLine();
        Bundle args = new Bundle();
        args.putInt(Constants.PAGE_INDEX, page);
        timeLineFragment.setArguments(args);
        return timeLineFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_timeline;
    }

    @Override
    public void initViews(View parent, Bundle savedInstanceState) {
        super.initViews(parent, savedInstanceState);
        ButterKnife.bind(this, parent);

        service = ((TweetApplication) getActivity().getApplication()).getNetworkService();
        presenter = new TweetsPresenter(this, service);

        token = InstructionPreferences.readString(getActivity(), InstructionPreferences.OAUTH_TOKEN, "");
        tokenSecret = InstructionPreferences.readString(getActivity(), InstructionPreferences.OAUTH_TOKEN_SECRET, "");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (userTimeLineList == null || userTimeLineList.size() == 0) {
            presenter.loadUserTimeLine(token, tokenSecret);
        } else {
            errResp.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            listView.setAdapter(null);
            listView.setAdapter(new TweetItemAdapter(getActivity(), R.layout.tweet_item, userTimeLineList));

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.rxUnSubscribe();
    }

    @Override
    public void showRessults(Object response) {
        userTimeLineList = (List<TweetItem>) response;
        if (!isAdded()) {
            return;
        }
        listView.setAdapter(null);
        listView.setAdapter(new TweetItemAdapter(getActivity(), R.layout.tweet_item, userTimeLineList));

        errResp.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}