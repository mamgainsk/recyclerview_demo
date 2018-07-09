package com.org.demowipro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.org.demowipro.Adapter.RecyclerViewAdapter;
import com.org.demowipro.NetworkingService.ImageAPIInterface;
import com.org.demowipro.RequestPOJO.RowContentInfo;
import com.org.demowipro.RequestPOJO.RowDescription;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<RowDescription> rowDescriptions;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.staggered_list);

        getDataFromAPI();

    }

    /**
     * Use of retrofit to consume JSON API and send data to Adapter class
     **/

    private void getDataFromAPI() {

        // Use of HttpLoggingInterceptor is redundant: written here  to show logs

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ImageAPIInterface imageAPIInterface = retrofit.create(ImageAPIInterface.class);
        Call<RowContentInfo> imagesResponseCall = imageAPIInterface.getJSONResponse();
        imagesResponseCall.enqueue(new Callback<RowContentInfo>() {
            @Override
            public void onResponse(@NonNull Call<RowContentInfo> call, @NonNull Response<RowContentInfo> response) {

                Log.d(TAG, "Row Response : " + response.body());
                RowContentInfo imageURLInfoListBean1 = response.body();

                if (imageURLInfoListBean1 != null) {
                    rowDescriptions = imageURLInfoListBean1.getRows();
                    recyclerViewAdapter = new RecyclerViewAdapter(rowDescriptions, MainActivity.this);
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }

            }

            @Override
            public void onFailure(@NonNull Call<RowContentInfo> call, @NonNull Throwable t) {
                Log.e(TAG, "Failed : " + t.getLocalizedMessage());
            }
        });
    }
}
