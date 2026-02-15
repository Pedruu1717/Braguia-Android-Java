package com.braguia.model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PinDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Pin> cats);

    @Query("SELECT DISTINCT * FROM pin")
    LiveData<List<Pin>> getPins();

    @Query("DELETE FROM pin")
    void deleteAll();

    @Query("SELECT * FROM pin WHERE id LIKE :id")
    LiveData<Pin> getPinById(String id);
}