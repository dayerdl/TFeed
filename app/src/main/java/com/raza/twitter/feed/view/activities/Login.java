package com.raza.twitter.feed.view.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.raza.twitter.feed.R;
import com.raza.twitter.feed.bLogic.Utils;
import com.raza.twitter.feed.network.AccessTokenGet;
import com.raza.twitter.feed.network.GetToken;
import com.raza.twitter.feed.prefs.InstructionPreferences;
import com.raza.twitter.feed.presenter.ViewInterface;
import com.raza.twitter.feed.view.widget.AlertDialogManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created by jaffarraza on 10/08/16.
 */
public class Login extends BaseActivity implements ViewInterface {

    @Bind(R.id.login)
    Button login;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    // Alert Dialog Manager
    private AlertDialogManager alert = new AlertDialogManager();

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ButterKnife.bind(this);
        String accessToken = InstructionPreferences.readString(this, InstructionPreferences.OAUTH_TOKEN, null);
        String tokenSecret = InstructionPreferences.readString(this, InstructionPreferences.OAUTH_TOKEN_SECRET, null);
        if (accessToken == null || tokenSecret == null) {
            return;
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showRessults(Object response) {
        if (response != null &&  (response instanceof RequestToken)) {
            RequestToken reqToken = (RequestToken) response;
            launchWebView(reqToken);
            return;
        }

        if (response != null &&  (response instanceof AccessToken)) {
            AccessToken reqToken = (AccessToken) response;
            String accessToken = reqToken.getToken();
            String tokenSecret = reqToken.getTokenSecret();
            if (accessToken == null || accessToken.equals("") || tokenSecret == null || tokenSecret.equals("")) {
                showFailure(ErrorType.RESPONSE, null);
                return;
            }
            InstructionPreferences.writeString(this, InstructionPreferences.OAUTH_TOKEN, accessToken);
            InstructionPreferences.writeString(this, InstructionPreferences.OAUTH_TOKEN_SECRET, tokenSecret);
            startActivity(new Intent(this, MainActivity.class));
            finish();
            progressBar.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            return;
        }
        showFailure(ErrorType.RESPONSE, null);
        return;
    }

    @Override
    public void showFailure(ErrorType type, Throwable throwable) {
        progressBar.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);
        String msg = "Some error occurred while performing login functionality.";
        if (type == ErrorType.NETWORK) {
            msg = "Please connect to working Internet connection";
        } else if (type == ErrorType.REJECTED) {
            msg = "You have rejected the twitter authentication.";
        }
        alert.showAlertDialog(this, "Error", msg, false);
    }

    @Override
    public void showInProcess() {
        progressBar.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
    }

    @OnClick(R.id.login)
    public void onClick() {
        if (!Utils.getInstance().isInternetAvailable(this)) {
            showFailure(ErrorType.NETWORK, null);
            return;
        }
        String accessToken = InstructionPreferences.readString(this, InstructionPreferences.OAUTH_TOKEN, null);
        String tokenSecret = InstructionPreferences.readString(this, InstructionPreferences.OAUTH_TOKEN_SECRET, null);
        if (accessToken == null || tokenSecret == null) {
            new GetToken(Login.this).execute();


//            presenter.login();
            return;
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }



    private void launchWebView(final RequestToken reqToken) {
        final Dialog auth_dialog = new Dialog(this);
        auth_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        String oauth_url = reqToken.getAuthorizationURL();

        auth_dialog.setContentView(R.layout.auth_dialog);
        WebView web = (WebView) auth_dialog.findViewById(R.id.webv);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(oauth_url);
        web.setWebViewClient(new WebViewClient() {
            boolean authComplete = false;
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("oauth_verifier") && authComplete == false){
                    authComplete = true;
//                    Log.e("Url",url);
                    Uri uri = Uri.parse(url);
                    String oauth_verifier = uri.getQueryParameter("oauth_verifier");

                    auth_dialog.dismiss();
                    new AccessTokenGet(Login.this, reqToken).execute(oauth_verifier);
                } else if(url.contains("denied")){
                    auth_dialog.dismiss();
                    showFailure(ErrorType.REJECTED, null);
                }
            }
        });
        auth_dialog.show();
        auth_dialog.setCancelable(true);
    }
}
