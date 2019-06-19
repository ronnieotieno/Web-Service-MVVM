package com.example.webservicewitharch;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Database(entities = Pictures.class, version = 1, exportSchema = false)
public abstract class PictureDataBase extends RoomDatabase {

    public static PictureDataBase instance;

    public abstract PicDao picDao();


    public static synchronized PictureDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PictureDataBase.class, "pic_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private PicDao picDao;

        public PopulateDbAsyncTask(PictureDataBase db) {
            picDao = db.picDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            RequestQueue mRequestQueue = Volley.newRequestQueue(this);
            String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("hits");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject hit = jsonArray.getJSONObject(i);

                            String creatorName = hit.getString("user");
                            String imageUrl = hit.getString("webformatURL");
                            int likeCount = hit.getInt("likes");

                            Pictures exampleItem = (new Pictures(imageUrl, creatorName, likeCount));
                           picDao.insert(exampleItem);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            mRequestQueue.add(request);
            return null;
        }

    }
}
