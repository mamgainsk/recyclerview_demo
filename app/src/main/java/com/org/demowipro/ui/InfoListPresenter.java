package com.org.demowipro.ui;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.org.demowipro.R;
import com.org.demowipro.database_module.AppDatabase;
import com.org.demowipro.database_module.DatabaseUtils;
import com.org.demowipro.networking_service.APICallback;
import com.org.demowipro.networking_service.NetworkingService;
import com.org.demowipro.preference_manager.PreferenceManagerClass;
import com.org.demowipro.request_pojo.RowContentInfo;
import com.org.demowipro.request_pojo.RowDescription;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

class InfoListPresenter implements InfoListContract.Presenter {

    private AppDatabase appDatabase;
    private InfoListContract.View view;

    @Override
    public void setView(InfoListContract.View view) {
        this.view = view;
    }

    @Override
    public void init() {
        appDatabase = AppDatabase.getAppDatabase(view.getContext());
    }

    @Override
    public void fetchData() {

        view.showProgressBar(true);


        new AsyncTask<Void, Void, List<RowDescription>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected List<RowDescription> doInBackground(Void... voids) {
                return DatabaseUtils.getRowDescription(appDatabase);
            }

            @Override
            protected void onPostExecute(List<RowDescription> rowDescriptionList) {
                super.onPostExecute(rowDescriptionList);

                view.showProgressBar(false);

                if (rowDescriptionList != null && rowDescriptionList.size() > 0) {
                    rowContentInfo = new RowContentInfo();
                    rowContentInfo.setRows(rowDescriptionList);
                    String title = PreferenceManagerClass.getString(InfoListActivity.this, PreferenceManagerClass.TITLE);
                    rowContentInfo.setTitle(title);
                    loadData();
                } else {
                    if (isNetworkAvailable()) {
                        getDataFromAPI(false);
                    } else {
                        showHideView(true);
                        msgTextView.setText(getResources().getString(R.string.check_internet));
                    }
                }

            }
        }.execute();
    }

    @Override
    public void getDataFromApi() {
        NetworkingService.fetchResponse(new APICallback() {
            @Override
            public void onResponse(Call<?> call, Response<?> response, int requestCode) {
                Log.d(TAG, "Row Response : " + response.body());
                rowContentInfo = (RowContentInfo) response.body();
                if (isForceRefresh)
                    lastItemPosition = 0;
                loadData();
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                idlingResource.decrement();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        DatabaseUtils.deleteTable(appDatabase);
                        PreferenceManagerClass.storeString(view.getContext(), PreferenceManagerClass.TITLE, rowContentInfo.getTitle());
                        DatabaseUtils.addRowDescription(appDatabase, rowContentInfo.getRows());
                    }
                });
            }

            @Override
            public void onFailure(Call<?> call, Throwable t, int requestCode) {
                Log.e(TAG, "Failed : " + t.getLocalizedMessage());
                msgTextView.setText(getResources().getString(R.string.failed_to_retrieve));
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                showHideView(true);
                idlingResource.decrement();
            }
        }, 1);
    }
}
