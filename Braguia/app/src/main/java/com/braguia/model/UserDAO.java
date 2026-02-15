package com.braguia.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("DELETE FROM user WHERE id = (SELECT id FROM user ORDER BY id LIMIT 1)")
    void deleteAll();

    @Query("SELECT * FROM user ORDER BY email ASC")
    LiveData<List<User>> getAlphabetizedUsers();

    @Query("SELECT email FROM user")
    List<String> getAllEmails();
}
