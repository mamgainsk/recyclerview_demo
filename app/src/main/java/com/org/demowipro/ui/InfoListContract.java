package com.org.demowipro.ui;

import android.content.Context;
import android.support.test.espresso.idling.CountingIdlingResource;

import com.org.demowipro.request_pojo.RowContentInfo;
import com.org.demowipro.request_pojo.RowDescription;

import java.util.List;


/**
 * Interface designed to make communication between presenter and View
 */
public interface InfoListContract {

    interface View {
        Context getContext();

        void showProgressBar(boolean shouldShow);

        boolean isNetworkAvailable();

        void showNoDataMsg();

        void reInitListSupportVariable();

        void setToolbarTitle(String title);

        void showViews(boolean enabled);

        void showViewMsg(int rString);

        void enableListRefresh(boolean enabled);

        CountingIdlingResource getIdlingResource();
    }

    interface Presenter {
        void setView(View view);

        void init();

        void fetchData();

        void getDataFromApi();

        void loadData();

        List<RowDescription> getRowDescriptions();

        RowContentInfo getRowContentInfo();

        void setRowContentInfo(RowContentInfo rowContentInfo);

        void onStart();

        void onStop();

        int getRecentItemPosition();

        void setRecentItemPosition(int lastItemPosition);
    }

}
