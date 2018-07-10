package com.org.demowipro.database_module;

import com.org.demowipro.request_pojo.RowDescription;

import java.util.List;

public class DatabaseUtils {

    public static void addRowDescription(final AppDatabase db, List<RowDescription> rowDescription) {
        db.rowDescriptionDao().insertAll(rowDescription);
    }

    /**
     * @param db instance of Database
     * @return all stored Row Data
     */
    public static List<RowDescription> getRowDescription(final AppDatabase db) {
        return db.rowDescriptionDao().getAllData();
    }

    public static void deleteTable(final AppDatabase db) {
        db.rowDescriptionDao().deleteTable();
    }

}
