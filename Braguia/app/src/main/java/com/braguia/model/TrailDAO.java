package com.braguia.model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrailDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Trail> cats);

    @Query("SELECT DISTINCT * FROM trail")
    LiveData<List<Trail>> getTrails();

    @Query("DELETE FROM trail")
    void deleteAll();

    @Query("SELECT * FROM trail WHERE id LIKE :id")
    LiveData<Trail> getTrailById(String id);
}