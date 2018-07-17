package com.org.demowipro.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.org.demowipro.R;
import com.org.demowipro.adapter.RecyclerViewAdapter;
import com.org.demowipro.database_module.AppDatabase;
import com.org.demowipro.request_pojo.RowContentInfo;

public class InfoListActivity extends AppCompatActivity implements InfoListContract.View {

    private static final String EXTRA_DATA = "data";
    private static final String EXTRA_LAST_POSITION = "position";

    private RecyclerView recyclerView;

    private RecyclerViewAdapter recyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private AppCompatTextView msgTextView;


    InfoListContract.Presenter presenter = new InfoListPresenter();


    public CountingIdlingResource idlingResource = new CountingIdlingResource("Network Call");

    private SwipeRefreshLayout.OnRefreshListener refreshRecyclerViewListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getDataFromAPI(true);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.setView(this);
        presenter.onStart();
        presenter.init();
        setContentView(R.layout.activity_main);
        initializeViews();
        getDataFromAPI(true);
        swipeRefreshLayout.setOnRefreshListener(refreshRecyclerViewListener);

        dataExist(savedInstanceState);
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recycler_view);
        toolbar = findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        progressBar = findViewById(R.id.progress_bar);
        msgTextView = findViewById(R.id.msg_textview);
    }


    /**
     * Use of retrofit to consume JSON API and send data to Adapter class
     **/

    private void getDataFromAPI(final boolean isForceRefresh) {
        idlingResource.increment();
        showViews(false);
        presenter.getDataFromApi();
        showProgressBar(!isForceRefresh);
    }


    /**
     * @return whether internet is available or not
     */
    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void showNoDataMsg() {
        showViews(true);
        msgTextView.setText(getResources().getString(R.string.check_internet));
    }

    @Override
    public void reInitListSupportVariable() {
        recyclerViewAdapter = new RecyclerViewAdapter(presenter.getRowDescriptions());
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.scrollToPosition(presenter.getRecentItemPosition());
    }

    @Override
    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }


    /**
     * @param isListEmpty to check whether the list is empty or not
     */
    @Override
    public void showViews(boolean isListEmpty) {
        recyclerView.setVisibility(isListEmpty ? View.GONE : View.VISIBLE);
        msgTextView.setVisibility(isListEmpty ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showViewMsg(int rString) {
        msgTextView.setText(getResources().getString(rString));
    }

    @Override
    public void enableListRefresh(boolean enabled) {
        swipeRefreshLayout.setRefreshing(enabled);
        if (enabled) idlingResource.decrement();
        else idlingResource.increment();
    }


    /**
     * @param outState store data for Screen rotation
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int lastItemPosition = 0;
        if (recyclerViewAdapter != null) {
            LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
            lastItemPosition = layoutManager.findFirstVisibleItemPosition();
        }
        outState.putParcelable(EXTRA_DATA, presenter.getRowContentInfo());
        outState.putInt(EXTRA_LAST_POSITION, lastItemPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dataExist(savedInstanceState);
    }

    private void dataExist(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_DATA) && savedInstanceState.containsKey(EXTRA_LAST_POSITION)) {
            RowContentInfo rowContentInfo = savedInstanceState.getParcelable(EXTRA_DATA);
            presenter.setRowContentInfo(rowContentInfo);
            int lastItemPosition = savedInstanceState.getInt(EXTRA_LAST_POSITION, 0);
            presenter.setRecentItemPosition(lastItemPosition);
            if (rowContentInfo != null) {
                //display data
                presenter.loadData();
            } else {
                fetchData();
            }
        } else {
            fetchData();
        }
    }

    private void fetchData() {
        presenter.fetchData();
    }

    @VisibleForTesting
    public CountingIdlingResource getIdlingResource() {
        return idlingResource;
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void showProgressBar(boolean shouldShow) {
        progressBar.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }
}
