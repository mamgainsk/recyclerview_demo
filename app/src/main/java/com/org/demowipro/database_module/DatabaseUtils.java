package com.org.demowipro.database_module;

import android.util.Log;

import com.org.demowipro.events.DBEvents;
import com.org.demowipro.request_pojo.RowDescription;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DatabaseUtils {

    public static void addRowDescription(final AppDatabase db, List<RowDescription> rowDescription) {
        db.rowDescriptionDao().insertAll(rowDescription);
    }

    /**
     * @param db instance of Database
     * @return all stored Row Data
     */
    public static void retrieveRowDescription(final AppDatabase db) {
        Log.d("DbUtils", "Method called");
        List<RowDescription> rows = db.rowDescriptionDao().getAllData();
        Log.d("DbUtils", "db retrieved");
        EventBus.getDefault().post(new DBEvents.DbRetrieved(rows));
        Log.d("DbUtils", "Event sent");
    }

    public static void deleteTable(final AppDatabase db) {
        db.rowDescriptionDao().deleteTable();
    }

}
