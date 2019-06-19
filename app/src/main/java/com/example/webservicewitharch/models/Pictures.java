package com.example.webservicewitharch.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pics_table")
public class Pictures {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "url")
    public String mImageUrl;
    @ColumnInfo(name = "creator")
    public String mCreator;
    @ColumnInfo(name = "likes")
    public int mLikes;

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

    @NonNull
    @Override
    public String toString() {
        return "Pictures{" +
                "mImageUrl='" + mImageUrl + '\'' +
                ", mCreator='" + mCreator + '\'' +
                ", mLikes=" + mLikes +
                '}';
    }
}
