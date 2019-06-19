package com.example.webservicewitharch.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.webservicewitharch.models.Pictures;
import com.example.webservicewitharch.utils.ThreadingExecutors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Database(entities = Pictures.class, version = 2, exportSchema = false)
public abstract class PictureDataBase extends RoomDatabase {

    private static PictureDataBase instance;

    public abstract PicDao picDao();


    public static synchronized PictureDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PictureDataBase.class, "pic_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            ThreadingExecutors.getInstance().networkIO().execute(() -> getData(context));
                        }
                    })
                    //TODO Use A class dedicated to populating the database on a background thread
                    .allowMainThreadQueries()//Temporary Hack
                    .build();
        }
        return instance;
    }

    private static void getData(Context context) {
        List<Pictures> mPictureList = new ArrayList<>();
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("hits");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject hit = jsonArray.getJSONObject(i);

                    String creatorName = hit.getString("user");
                    String imageUrl = hit.getString("webformatURL");
                    int likeCount = hit.getInt("likes");

                    Pictures exampleItem = (new Pictures(imageUrl, creatorName, likeCount));
                    //add pictures onto a list,less expensive that writing to a database in a loop
                    mPictureList.add(exampleItem);
                }
                //Insert all at once
                getInstance(context).picDao().insert(mPictureList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        mRequestQueue.add(request);
    }

}
