package com.example.zepto;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface CartDao {
    @Insert
    void insertItem(databaseitem item);

    @Query("SELECT * FROM cart_table")
    LiveData<List<databaseitem>> getAllItems();

    @Delete
    void delete(databaseitem item);
}
