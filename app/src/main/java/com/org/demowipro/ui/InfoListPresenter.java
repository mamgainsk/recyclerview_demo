package com.org.demowipro.ui;

import android.util.Log;

import com.org.demowipro.R;
import com.org.demowipro.database_module.AppDatabase;
import com.org.demowipro.database_module.DatabaseUtils;
import com.org.demowipro.events.DBEvents;
import com.org.demowipro.networking_service.APICallback;
import com.org.demowipro.networking_service.NetworkingService;
import com.org.demowipro.preference_manager.TitleSessionManager;
import com.org.demowipro.request_pojo.RowContentInfo;
import com.org.demowipro.request_pojo.RowDescription;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

class InfoListPresenter implements InfoListContract.Presenter {

    private AppDatabase appDatabase;
    private InfoListContract.View view;
    private List<RowDescription> rowDescriptions;
    private RowContentInfo rowContentInfo;
    private int recentItemPosition = 0;

    @Override
    public void setView(InfoListContract.View view) {
        this.view = view;
    }

    @Override
    public void init() {
        appDatabase = AppDatabase.getAppDatabase(view.getContext());
        rowContentInfo = new RowContentInfo();
    }

    @Override
    public void fetchData() {
        view.showProgressBar(true);
        DatabaseUtils.retrieveRowDescription(appDatabase);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onDbRetrieved(DBEvents.DbRetrieved event) {
        view.showProgressBar(false);
        List<RowDescription> rowDescriptionList = event.getRows();
        if (rowDescriptionList != null && rowDescriptionList.size() > 0) {
            RowContentInfo rowContentInfo = new RowContentInfo();
            rowContentInfo.setRows(rowDescriptionList);
            String title = TitleSessionManager.getString(view.getContext(), TitleSessionManager.TITLE);
            rowContentInfo.setTitle(title);
            loadData();
        } else {
            if (view.isNetworkAvailable()) {
                getDataFromApi();
            } else {
                view.showNoDataMsg();
            }
        }
    }


    @Override
    public void loadData() {
        if (rowContentInfo != null) {
            rowDescriptions = rowContentInfo.getRows();
            view.setToolbarTitle(rowContentInfo.getTitle());
            view.reInitListSupportVariable();
            view.showViews(false);
        } else {
            view.showViewMsg(R.string.no_data);
            view.showViews(true);
        }
    }

    /**
     * API call using retrofit
     */
    @Override
    public void getDataFromApi() {
        view.getIdlingResource().increment();
        view.showProgressBar(true);
        NetworkingService.fetchResponse(new APICallback() {
            @Override
            public void onResponse(Call<?> call, Response<?> response, int requestCode) {
                Log.d(TAG, "Row Response : " + response.body());
                rowContentInfo = (RowContentInfo) response.body();
                loadData();
                view.showProgressBar(false);
                view.enableListRefresh(false);
                DatabaseUtils.deleteTable(appDatabase);
                TitleSessionManager.storeString(view.getContext(), TitleSessionManager.TITLE, rowContentInfo.getTitle());
                DatabaseUtils.addRowDescription(appDatabase, rowContentInfo.getRows());
            }

            @Override
            public void onFailure(Call<?> call, Throwable t, int requestCode) {
                Log.e(TAG, "Failed : " + t.getLocalizedMessage());
                view.showViewMsg(R.string.failed_to_retrieve);
                view.showProgressBar(false);
                view.showViews(true);
                view.enableListRefresh(false);
            }
        }, 1);
    }

    @Override
    public List<RowDescription> getRowDescriptions() {
        return rowDescriptions;
    }

    @Override
    public RowContentInfo getRowContentInfo() {
        return rowContentInfo;
    }

    @Override
    public void setRowContentInfo(RowContentInfo rowContentInfo) {
        this.rowContentInfo = rowContentInfo;
    }

    @Override
    public void onStart() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public int getRecentItemPosition() {
        return recentItemPosition;
    }

    @Override
    public void setRecentItemPosition(int lastItemPosition) {
        recentItemPosition = lastItemPosition;
    }
}
