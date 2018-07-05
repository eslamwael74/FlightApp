package com.example.eslamwael.flightapp.Network;

import com.example.eslamwael.flightapp.Utils.Const;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by eslamwael74 on 7/5/2018.
 * Email: eslamwael74@outlook.com.
 */
public class RetrofitWebService {

    private static final String TAG = RetrofitWebService.class.getSimpleName();
    private static final Map<String, RetrofitService> SERVICE_MAP = new HashMap<>();
    public static final String url = Const.BASE_URL;


    private RetrofitWebService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        SERVICE_MAP.put(url, retrofit.create(RetrofitService.class));

    }

    public static RetrofitService getService() {
        if (null == SERVICE_MAP.get(url))
            new RetrofitWebService();
        return SERVICE_MAP.get(url);
    }



}
