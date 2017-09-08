package com.algorepublic.communicator;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.algorepublic.communicator.interfaces.ApiInterface;
import com.algorepublic.communicator.requests.ApiClient;
import com.algorepublic.communicator.utils.TinyDB;
import com.algorepublic.communicator.utils.Util;

/**
 * Created by adilnazir on 24/07/2017.
 */

public class Communicator extends Application {
    public SharedPreferences appSharedPrefs;
    public SharedPreferences.Editor prefsEditor;
    public String SHARED_NAME = "com.algorepublic.Communicator";
    public static TinyDB db;
    private static Communicator instance;
    public static Context context;
    private static final String TAG = Communicator.class.getName();
    public static ApiInterface apiService;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initPreferences();
        configRetrofit();
        if (Util.LOG_ENABLED) {
            Log.v(TAG, "public void onCreate()");
        }
        Communicator.context = getApplicationContext();

    }

    public void initPreferences() {
        this.appSharedPrefs = getSharedPreferences(SHARED_NAME,
                Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
        db = new TinyDB(getApplicationContext());
    }

    public static void configRetrofit() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public static Communicator getInstance() {
        return instance;
    }

    public static ApiInterface getApiService() {
        return apiService;
    }

    /**
     *
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();

        Log.i(TAG, "Freeing memory ...");

    }

    /**
     *
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (Util.LOG_ENABLED) {
            Log.v(TAG, "public void onTrimMemory (int level)");
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
