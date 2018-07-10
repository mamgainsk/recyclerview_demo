package com.org.demowipro;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.org.demowipro.adapter.RecyclerViewAdapter;
import com.org.demowipro.networking_service.APICallback;
import com.org.demowipro.networking_service.NetworkingService;
import com.org.demowipro.request_pojo.RowContentInfo;
import com.org.demowipro.request_pojo.RowDescription;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_DATA = "data";
    private static final String EXTRA_LAST_POSITION = "position";

    private RecyclerView recyclerView;
    private List<RowDescription> rowDescriptions;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private AppCompatTextView msgTextView;
    private RowContentInfo rowContentInfo;
    private int lastItemPosition;

    private SwipeRefreshLayout.OnRefreshListener refreshRecyclerViewListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getDataFromAPI(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        swipeRefreshLayout.setOnRefreshListener(refreshRecyclerViewListener);

        dataExist(savedInstanceState);


    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.staggered_list);
        toolbar = findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        progressBar = findViewById(R.id.progress_bar);
        msgTextView = findViewById(R.id.msg_textview);
    }


    /**
     * Use of retrofit to consume JSON API and send data to Adapter class
     **/

    private void getDataFromAPI(boolean isForceRefresh) {
        showHideView(false);
        if (!isForceRefresh)
            progressBar.setVisibility(View.VISIBLE);

        NetworkingService.fetchResponse(new APICallback() {
            @Override
            public void onResponse(Call<?> call, Response<?> response, int requestCode) {
                Log.d(TAG, "Row Response : " + response.body());
                rowContentInfo = (RowContentInfo) response.body();

                loadData();
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<?> call, Throwable t, int requestCode) {
                Log.e(TAG, "Failed : " + t.getLocalizedMessage());
                msgTextView.setText(getResources().getString(R.string.failed_to_retrieve));
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                showHideView(true);


            }
        }, 1);
    }

    private void loadData() {
        if (rowContentInfo != null) {
            rowDescriptions = rowContentInfo.getRows();
            toolbar.setTitle(rowContentInfo.getTitle());
            recyclerViewAdapter = new RecyclerViewAdapter(rowDescriptions);
            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.scrollToPosition(lastItemPosition);
            showHideView(false);

        } else {
            msgTextView.setText(getResources().getString(R.string.no_data));
            showHideView(true);


        }
    }

    //---------------------- Internet Connection Check ---------------------------
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //------------------------ Empty List Check ----------------------------------
    private void showHideView(boolean isListEmpty) {
        if (isListEmpty) {
            recyclerView.setVisibility(View.GONE);
            msgTextView.setVisibility(View.VISIBLE);

        } else {
            recyclerView.setVisibility(View.VISIBLE);
            msgTextView.setVisibility(View.GONE);
        }
    }

    //------------------------ Screen Orientation Handled ----------------------------------

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int lastItemPosition = 0;
        if (recyclerViewAdapter != null) {
            LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
            lastItemPosition = layoutManager.findFirstVisibleItemPosition();
        }
        outState.putParcelable(EXTRA_DATA, rowContentInfo);
        outState.putInt(EXTRA_LAST_POSITION, lastItemPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dataExist(savedInstanceState);
    }

    private void dataExist(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_DATA) && savedInstanceState.containsKey(EXTRA_LAST_POSITION)) {

            rowContentInfo = savedInstanceState.getParcelable(EXTRA_DATA);
            lastItemPosition = savedInstanceState.getInt(EXTRA_LAST_POSITION, 0);
            if (rowContentInfo != null) {
                //display data
                loadData();
            } else {
                fetchData();
            }
        } else {

            fetchData();
        }
    }

    private void fetchData() {
        if (isNetworkAvailable()) {
            getDataFromAPI(false);
        } else {
            showHideView(true);
            msgTextView.setText(getResources().getString(R.string.check_internet));
        }
    }
}
