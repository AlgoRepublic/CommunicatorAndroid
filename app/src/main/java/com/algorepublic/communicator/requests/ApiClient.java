package com.algorepublic.communicator.requests;


import com.algorepublic.communicator.cert.SelfSigningClientBuilder;
import com.algorepublic.communicator.utils.Constants;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ApiClient {
    public static final String BASE_URL = Constants.BASE_URL;
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(SelfSigningClientBuilder.createClient())
                    .build();
        }
        return retrofit;
    }
}