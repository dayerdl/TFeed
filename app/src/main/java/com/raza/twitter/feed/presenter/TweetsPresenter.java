package com.raza.twitter.feed.presenter;

import com.raza.twitter.feed.model.TweetItem;
import com.raza.twitter.feed.model.TweetResponse;
import com.raza.twitter.feed.network.NetworkService;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by jaffarraza on 18/08/16.
 */
public class TweetsPresenter implements PresenterInteractor {

    private ViewInterface view;
    private NetworkService service;
    private Subscription subscription;

    public TweetsPresenter(ViewInterface view, NetworkService service){
        this.view = view;
        this.service = service;
    }


    public void rxUnSubscribe(){
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }


    @Override
    public void loadUserTimeLine(String token, String tokenSecret) {
        view.showInProcess();
        Observable<List<TweetItem>> friendResponseObservable = (Observable<List<TweetItem>>)
                service.getPreparedObservable(service.getAPI(token, tokenSecret).getUserTweets(), TweetResponse.class, true, true);
        subscription = friendResponseObservable.subscribe(new Observer<List<TweetItem>>() {
            @Override
            public void onCompleted() {        }
            @Override
            public void onError(Throwable e) {
                view.showFailure(ViewInterface.ErrorType.RESPONSE, e);
            }
            @Override
            public void onNext(List<TweetItem> response) {
                view.showRessults(response);
            }
        });
    }

    @Override
    public void searchFeedItems(String token, String tokenSecret, String searchItem) {
        view.showInProcess();

        Observable<List<TweetItem>> friendResponseObservable = (Observable<List<TweetItem>>)
                service.getPreparedObservable(service.getAPI(token, tokenSecret).getUserTweets(), TweetResponse.class, true, true);
        subscription = friendResponseObservable.subscribe(new Observer<List<TweetItem>>() {
            @Override
            public void onCompleted() {        }
            @Override
            public void onError(Throwable e) {
                view.showFailure(ViewInterface.ErrorType.RESPONSE, e);
            }
            @Override
            public void onNext(List<TweetItem> response) {
                view.showRessults(response);
            }
        });
    }
}
