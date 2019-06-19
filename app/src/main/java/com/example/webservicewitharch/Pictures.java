package com.example.webservicewitharch;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pics_table")
public class Pictures {

    @PrimaryKey(autoGenerate = true)
    int id;
    String mImageUrl;
    String mCreator;
    int mLikes;

    public Pictures(String mImageUrl, String mCreator, int mLikes) {
        this.mImageUrl = mImageUrl;
        this.mCreator = mCreator;
        this.mLikes = mLikes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmCreator() {
        return mCreator;
    }

    public int getmLikes() {
        return mLikes;
    }

}
