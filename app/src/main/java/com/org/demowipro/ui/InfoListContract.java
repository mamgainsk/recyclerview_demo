package com.org.demowipro.ui;

import android.content.Context;

public interface InfoListContract {

    interface View {
        Context getContext();

        void showProgressBar(boolean shouldShow);
    }

    interface Presenter {
        void setView(View view);

        void init();

        void fetchData();

        void getDataFromApi();
    }

}
