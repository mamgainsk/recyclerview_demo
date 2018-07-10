package com.org.demowipro.networking_service;

import android.support.annotation.NonNull;

import com.org.demowipro.BuildConfig;
import com.org.demowipro.request_pojo.RowContentInfo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkingService {

    private static Retrofit init() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void fetchResponse(final APICallback apiCallback, final int requestCode) {
        ImageAPIInterface imageAPIInterface = init().create(ImageAPIInterface.class);

        Call<RowContentInfo> imagesResponseCall = imageAPIInterface.getJSONResponse();
        imagesResponseCall.enqueue(new Callback<RowContentInfo>() {
            @Override
            public void onResponse(@NonNull Call<RowContentInfo> call, @NonNull Response<RowContentInfo> response) {
                apiCallback.onResponse(call, response, requestCode);
            }

            @Override
            public void onFailure(@NonNull Call<RowContentInfo> call, @NonNull Throwable t) {
                apiCallback.onFailure(call, t, requestCode);
            }
        });
    }

}
