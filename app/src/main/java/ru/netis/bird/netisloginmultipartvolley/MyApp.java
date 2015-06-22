package ru.netis.bird.netisloginmultipartvolley;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyApp extends Application {

    public static final String TAG = MyApp.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private CookieManager mCookieManager;

    private static MyApp mInstance;

    public static synchronized MyApp getInstance() {
        return MyApp.mInstance;
    }

    public <T> void addToRequestQueue(final Request<T> req) {
        req.setTag(MyApp.TAG);
        this.getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(final Request<T> req, final String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? MyApp.TAG : tag);
        this.getRequestQueue().add(req);
    }

    public void cancelPendingRequests(final Object tag) {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.cancelAll(tag);
        }
    }

    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
/*


            DefaultHttpClient mDefaultHttpClient = new DefaultHttpClient();

            final ClientConnectionManager mClientConnectionManager = mDefaultHttpClient.getConnectionManager();
            final HttpParams mHttpParams = mDefaultHttpClient.getParams();
            final ThreadSafeClientConnManager mThreadSafeClientConnManager = new ThreadSafeClientConnManager( mHttpParams, mClientConnectionManager.getSchemeRegistry() );

            mDefaultHttpClient = new DefaultHttpClient( mThreadSafeClientConnManager, mHttpParams );

            final HttpStack httpStack = new HttpClientStack( mDefaultHttpClient );
*/

//            mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new MyHurlStack());
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public CookieManager getCookieManager() {
        if (mCookieManager == null) {
            mCookieManager = (CookieManager) CookieManager.getDefault();
            if (mCookieManager == null) {
                mCookieManager = new CookieManager();
                mCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
                CookieManager.setDefault(mCookieManager);
            }
        }
        return mCookieManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.mInstance = this;
    }
}