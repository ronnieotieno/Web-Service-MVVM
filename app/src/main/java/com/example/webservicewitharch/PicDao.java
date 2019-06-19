package com.example.webservicewitharch;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PicDao {

    @Insert
    void  insert(Pictures pictures);

    @Query("SELECT * FROM pics_table")
    LiveData<List<Pictures>> getAllPictures();

}
