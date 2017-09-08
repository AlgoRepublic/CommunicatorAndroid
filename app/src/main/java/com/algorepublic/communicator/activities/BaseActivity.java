package com.algorepublic.communicator.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;

import com.algorepublic.communicator.Communicator;
import com.algorepublic.communicator.R;
import com.algorepublic.communicator.model.ActListModel;
import com.algorepublic.communicator.model.ActSchedule2Model;
import com.algorepublic.communicator.model.ActTypeListModel;
import com.algorepublic.communicator.model.LabourModel;
import com.algorepublic.communicator.utils.Constants;
import com.algorepublic.communicator.utils.Util;
import com.google.gson.Gson;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class BaseActivity extends AppCompatActivity {
    private State lifecycleState = null;
    public static InputMethodManager imeManager;
    private ProgressDialog dialog;

    protected static final int SPLASH_TIME = 2 * 1000;
    protected static final int API_TIME = 1000;
    protected Intent intent;
    protected RotateAnimation anim;
    protected Handler apiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLifecycleState(State.CREATED);
        imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
    }

    Runnable loadSplashThread = new Runnable() {
        @Override
        public void run() {
            startApp();
        }
    };


    public void startApp() {
        intent = new Intent(this, CommunicatorMain.class);
        startActivity(intent);
        finish();
    }

    private void startAuth() {
        intent = new Intent(this, CommunicatorAuth.class);
        startActivity(intent);
        finish();
    }

    Runnable loadLabourList = new Runnable() {

        @Override
        public void run() {
            callLabourList();
            apiHandler.postDelayed(loadActSchedule2, API_TIME);

        }
    };

    Runnable loadActSchedule2 = new Runnable() {

        @Override
        public void run() {
            callActSchedule2();
            apiHandler.postDelayed(loadAct, API_TIME);

        }
    };
    Runnable loadAct = new Runnable() {

        @Override
        public void run() {
            callActList();
            apiHandler.postDelayed(loadActType, API_TIME);

        }
    };
    Runnable loadActType = new Runnable() {

        @Override
        public void run() {
            callActTypeList();
//            apiHandler.postDelayed(load, API_TIME);

        }
    };


    /**
     * Web service request
     * save response in preference
     */
    public void callLabourList() {
        showDialog();
        Call<LabourModel> call = Communicator.getApiService().getLabourModel();
        call.enqueue(new Callback<LabourModel>() {
            @Override
            public void onResponse(Response<LabourModel> response, Retrofit retrofit) {
                removeDialog();
                if (response.body().laboursList != null) {
                    Gson gson = new Gson();
                    String respone = gson.toJson(response.body());
                    Communicator.db.putString(Constants.RESPONSE_GSON_LABOUR_LIST, respone);
//                    startAuth();
                    Log.d("RESPONS", "GSON_LABOUR_LIST:" + Communicator.db.getString(Constants.RESPONSE_GSON_LABOUR_LIST));

                } else {
                    Util.showToast(BaseActivity.this, "Unexpected response from server");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("ERROR", "ERROR:" + t.getMessage());
                Util.showToast(BaseActivity.this, "Server not responding");
                removeDialog();
            }
        });


    }


    /**
     * Web service request
     * save response in preference
     */
    public void callActList() {
        showDialog();
        Call<ActListModel> call = Communicator.getApiService().getActList();
        call.enqueue(new Callback<ActListModel>() {
            @Override
            public void onResponse(Response<ActListModel> response, Retrofit retrofit) {
                removeDialog();
                if (response.body().valueList != null) {
                    Gson gson = new Gson();
                    String respone = gson.toJson(response.body());
                    Communicator.db.putString(Constants.RESPONSE_GSON_ACT_LIST, respone);
//                    startAuth();
                    Log.d("RESPONSE", "GSON_ACT_LIST:" + Communicator.db.getString(Constants.RESPONSE_GSON_ACT_LIST));

                } else {
                    Util.showToast(BaseActivity.this, "Unexpected response from server");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("ERROR", "ERROR:" + t.getMessage());
                Util.showToast(BaseActivity.this, "Server not responding");
                removeDialog();
            }
        });


    }

    /**
     * Web service request
     * save response in preference
     */
    public void callActTypeList() {
        showDialog();
        Call<ActTypeListModel> call = Communicator.getApiService().getActTypeList();
        call.enqueue(new Callback<ActTypeListModel>() {
            @Override
            public void onResponse(Response<ActTypeListModel> response, Retrofit retrofit) {
                removeDialog();
                if (response.body().valueList != null) {
                    Gson gson = new Gson();
                    String respone = gson.toJson(response.body());
                    Communicator.db.putString(Constants.RESPONSE_GSON_ACT_TYPE_LIST, respone);
                    startAuth();
                    Log.d("RESPONSE", "GSON_ACT_TYPE_LIST:" + Communicator.db.getString(Constants.RESPONSE_GSON_ACT_TYPE_LIST));

                } else {
                    Util.showToast(BaseActivity.this, "Unexpected response from server");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("ERROR", "ERROR:" + t.getMessage());
                Util.showToast(BaseActivity.this, "Server not responding");
                removeDialog();
            }
        });


    }

    /**
     * Web service request
     * save response in preference
     */
    public void callActSchedule2() {
        showDialog();
        Call<ActSchedule2Model> call = Communicator.getApiService().getActSchedule();
        call.enqueue(new Callback<ActSchedule2Model>() {
            @Override
            public void onResponse(Response<ActSchedule2Model> response, Retrofit retrofit) {
                removeDialog();
                if (response.body().valueList != null) {
                    Gson gson = new Gson();
                    String respone = gson.toJson(response.body());
                    Communicator.db.putString(Constants.RESPONSE_GSON_ACT_SCHEDULE_LIST, respone);
                    startAuth();
                    Log.d("RESPONSE", "GSON_ACT_SCHEDULE_LIST:" + Communicator.db.getString(Constants.RESPONSE_GSON_ACT_SCHEDULE_LIST));

                } else {
                    Util.showToast(BaseActivity.this, "Unexpected response from server");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("ERROR", "ERROR:" + t.getMessage());
                Util.showToast(BaseActivity.this, "Server not responding");
                removeDialog();
            }
        });


    }


    public void removeDialog() {
        dialog.dismiss();
    }


    public void showDialog() {

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");
        dialog.setMessage("Please wait...");

        dialog.show();
    }


    public File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "FFM");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp
                + ".jpg");
        return mediaFile;
    }


    public void callFragmentWithReplace(int containerId, Fragment fragment, String tag) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment, tag)
                .setCustomAnimations(R.anim.slide_in_enter, R.anim.slide_in_exit,
                        R.anim.slide_pop_enter, R.anim.slide_pop_exit);
        if (tag != null)
            transaction.addToBackStack(tag)
                    .commit();
        else
            transaction
                    .commit();
    }

    public void callFragment(int containerId, Fragment fragment, String tag) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment, tag)
                .setCustomAnimations(R.anim.slide_in_enter, R.anim.slide_in_exit,
                        R.anim.slide_pop_enter, R.anim.slide_pop_exit);
        if (tag != null)
            transaction.addToBackStack(tag)
                    .commit();
        else
            transaction
                    .commit();
    }


    public String Get24FormatTime(String date) {
        String a = date.replaceAll("\\D+", "");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(a));

        String delegate = "hh:mm";
        String time = (String) DateFormat.format(delegate, Calendar.getInstance().getTime());

        return (time);
    }

    public String GetDate(String date) {
        java.text.DateFormat df = new SimpleDateFormat("yy-d-M'T'hh:mm:ss");
        Date startDate;
        String newDateString = null;
        try {
            startDate = df.parse(date);
            newDateString = df.format(startDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        String[] date1 = newDateString.split("-");
        String[] date2 = date1[2].toString().split("T");
        return (date1[1] + "/" + date2[0] + "/" + date1[0] + " " + date2[1]);
    }

    public String Get12FormatTime1(Long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);

        String delegate = "hh:mm a";
        String time = (String) DateFormat.format(delegate, calendar.getTime());

        return (time);
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public String DateMilli(String date) {
        String a = date.replaceAll("\\D+", "");
        return a;
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public void hideKeyPad(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setLifecycleState(State.STARTED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLifecycleState(State.RESUMED);
    }

    @Override
    protected void onPause() {
        super.onPause();

        setLifecycleState(State.PAUSED);
    }


    @Override
    protected void onStop() {
        super.onStop();
        setLifecycleState(State.STOPPED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setLifecycleState(State.DESTROYED);
    }

    /**
     * @return the lifecycleState
     */
    public State getLifecycleState() {
        return lifecycleState;
    }

    /**
     * @param lifecycleState the lifecycleState to set
     */
    public void setLifecycleState(State lifecycleState) {
        this.lifecycleState = lifecycleState;
    }

    /**
     * The possibles states of an activity lifecycle.
     */
    public static enum State {
        CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTROYED;
    }

    ;
}
