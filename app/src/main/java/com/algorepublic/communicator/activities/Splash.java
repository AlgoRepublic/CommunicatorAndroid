package com.algorepublic.communicator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.algorepublic.communicator.Communicator;
import com.algorepublic.communicator.R;
import com.algorepublic.communicator.utils.CallBack;
import com.algorepublic.communicator.utils.Constants;

/**
 * Created by adilnazir on 24/07/2017.
 */

public class Splash extends BaseActivity {

    private static final int SPLASH_TIME = 2 * 1000;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        if (Communicator.db.getBoolean(Constants.IS_LOGIN))
            apiHandler.postDelayed(loadSplashThread, SPLASH_TIME);
        else
            apiHandler.postDelayed(loadLabourList, API_TIME);

    }


    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

}
