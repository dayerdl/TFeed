package com.raza.twitter.feed.presenter;

/**
 * Created by jaffarraza on 18/08/16.
 */
public interface ViewInterface {
    public void showRessults(Object response);
    public void showFailure(ErrorType type, Throwable throwable);
    public void showInProcess();

    public enum ErrorType {
        NETWORK,
        RESPONSE,
        REJECTED
    }
}
