package com.example.webservicewitharch.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.webservicewitharch.models.Pictures;

import java.util.List;

@Dao
public interface PicDao {

    @Insert
    void insert(List<Pictures> pictures);//Insert List of Pics

    @Query("SELECT * FROM pics_table")
    LiveData<List<Pictures>> getAllPictures();

}
