package com.braguia.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EdgeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Edge> cats);

    @Query("SELECT DISTINCT * FROM edge")
    LiveData<List<Edge>> getEdges();

    @Query("SELECT * FROM edge WHERE edge_trail LIKE :trailId")
    LiveData<List<Edge>> getEdgesByTrailId(String trailId);

    @Query("DELETE FROM edge")
    void deleteAll();
}
