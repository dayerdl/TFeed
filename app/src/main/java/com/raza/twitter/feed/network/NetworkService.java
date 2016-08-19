package com.raza.twitter.feed.network;


import android.support.v4.util.LruCache;
import android.util.Base64;

import com.raza.twitter.feed.model.TweetItem;
import com.raza.twitter.feed.prefs.Constants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jaffarraza on 18/08/16.
 */
public class NetworkService {

    private static String baseUrl ="https://api.twitter.com/1.1/";
    private static String userTimeLine = baseUrl +"statuses/user_timeline.json";
    String method = "GET";
    String url = userTimeLine;
    String oAuthNonce = String.valueOf(System.currentTimeMillis());
    String oAuthSignatureMethod = "HMAC-SHA1";
    String oAuthTimestamp = time();
    String oAuthVersion = "1.0";

    private NetworkAPI networkAPI;
    private OkHttpClient okHttpClient;
    private LruCache<Class<?>, Observable<?>> apiObservables;

    String oAuthConsumerKey = Constants.TWITTER_CONSUMER_KEY;
    String oAuthConsumerSecret = Constants.TWITTER_CONSUMER_SECRET; //<--- DO NOT SHARE THIS VALUE




    public NetworkService(){

    }


    /**
     * Method to return the API interface.
     * @return
     */
    public NetworkAPI getAPI(String token, String tokenSecret) {
        if (okHttpClient == null) {
            okHttpClient = buildClient(token, tokenSecret);
        }
        apiObservables = new LruCache<>(10);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        networkAPI = retrofit.create(NetworkAPI.class);
        return  networkAPI;
    }


    /**
     * Method to build and return an OkHttpClient so we can set/get
     * headers quickly and efficiently.
     * @return
     */
    public OkHttpClient buildClient(final String token, final String tokenSecret){
        final String header = makeHeaderValue(token, tokenSecret);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

                builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", ""+header)
                .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));


        return  builder.build();
    }

    private String makeHeaderValue(String oAuthAccessToken, String oAuthAccessTokenSecret) {
        try {

            String signatureBaseString1 = method;
            String signatureBaseString2 = url;
            String signatureBaseString3Templ = "oauth_consumer_key=%s&oauth_nonce=%s&oauth_signature_method=%s&oauth_timestamp=%s&oauth_token=%s&oauth_version=%s";
            String signatureBaseString3 = String.format(signatureBaseString3Templ,
                    oAuthConsumerKey,
                    oAuthNonce,
                    oAuthSignatureMethod,
                    oAuthTimestamp,
                    oAuthAccessToken,
                    oAuthVersion);

            String signatureBaseStringTemplate = "%s&%s&%s";
            String signatureBaseString =  String.format(signatureBaseStringTemplate,
                    URLEncoder.encode(signatureBaseString1, "UTF-8"),
                    URLEncoder.encode(signatureBaseString2, "UTF-8"),
                    URLEncoder.encode(signatureBaseString3, "UTF-8"));
//
            String compositeKey = URLEncoder.encode(oAuthConsumerSecret, "UTF-8") + "&" + URLEncoder.encode(oAuthAccessTokenSecret, "UTF-8");
//
            String oAuthSignature =  computeSignature(signatureBaseString, compositeKey);
//
            String oAuthSignatureEncoded = URLEncoder.encode(oAuthSignature, "UTF-8");

            String authorizationHeaderValueTempl = "OAuth oauth_consumer_key=\"%s\", oauth_nonce=\"%s\", oauth_signature=\"%s\", oauth_signature_method=\"%s\", oauth_timestamp=\"%s\", oauth_token=\"%s\", oauth_version=\"%s\"";

            String authorizationHeaderValue = String.format(authorizationHeaderValueTempl,
                    oAuthConsumerKey,
                    oAuthNonce,
                    oAuthSignatureEncoded,
                    oAuthSignatureMethod,
                    oAuthTimestamp,
                    oAuthAccessToken,
                    oAuthVersion);

            return authorizationHeaderValue;

        } catch (Exception e) {

        }

        return "";
    }

    private String time() {
        long millis = System.currentTimeMillis();
        long secs = millis / 1000;
        return String.valueOf( secs );
    }

    private static String computeSignature(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException, Exception
    {

        SecretKey secretKey = null;

        byte[] keyBytes = keyString.getBytes();
        secretKey = new SecretKeySpec(keyBytes, "HMAC-SHA1");

        Mac mac = Mac.getInstance("HMAC-SHA1");

        mac.init(secretKey);

        byte[] text = baseString.getBytes();

        return new String(Base64.encode(mac.doFinal(text), 0)).trim();
    }

    /**
     * Method to clear the entire cache of observables
     */
    public void clearCache(){
        apiObservables.evictAll();
    }


    /**
     * Method to either return a cached observable or prepare a new one.
     *
     * @param unPreparedObservable
     * @param clazz
     * @param cacheObservable
     * @param useCache
     * @return Observable ready to be subscribed to
     */
    public Observable<?> getPreparedObservable(Observable<?> unPreparedObservable, Class<?> clazz, boolean cacheObservable, boolean useCache){

        Observable<?> preparedObservable = null;

        if(useCache)//this way we don't reset anything in the cache if this is the only instance of us not wanting to use it.
            preparedObservable = apiObservables.get(clazz);

        if(preparedObservable!=null)
            return preparedObservable;



        //we are here because we have never created this observable before or we didn't want to use the cache...

        preparedObservable = unPreparedObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        if(cacheObservable){
            preparedObservable = preparedObservable.cache();
            apiObservables.put(clazz, preparedObservable);
        }


        return preparedObservable;
    }


    /**
     * all the Service alls to use for the retrofit requests.
     */
    public interface NetworkAPI {

        @GET("statuses/user_timeline.json") //real endpoint
        Observable<List<TweetItem>> getUserTweets(
        );

        @GET("search/tweets.json") //real endpoint
        Observable<List<TweetItem>> getSearchFeed(
                @Query("q") String label
        );

    }

}
