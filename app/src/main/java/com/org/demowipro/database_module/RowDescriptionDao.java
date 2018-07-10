package com.org.demowipro.database_module;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.org.demowipro.request_pojo.RowDescription;

import java.util.List;

@Dao
public interface RowDescriptionDao {

    @Query("SELECT * FROM RowDescription")
    List<RowDescription> getAllData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RowDescription> rowDescriptionList);

    @Query("DELETE FROM RowDescription")
    void deleteTable();

}
