package com.example.webservicewitharch;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.webservicewitharch.data.PicDao;
import com.example.webservicewitharch.data.PictureDataBase;
import com.example.webservicewitharch.models.Pictures;

import java.util.Arrays;
import java.util.List;

public class PicsRepository {
    private PicDao picDao;
    private LiveData<List<Pictures>> allpics;

    public PicsRepository(Application application) {

        PictureDataBase dataBase = PictureDataBase.getInstance(application);
        picDao = dataBase.picDao();
        allpics = picDao.getAllPictures();
    }

    public void insert(Pictures pictures) {
        new InsertNoteAsyncTask(picDao).execute(pictures);
    }

    public LiveData<List<Pictures>> getAllPictures() {
        return allpics;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Pictures, Void, Void> {
        private PicDao picDao;

        private InsertNoteAsyncTask(PicDao picDao) {
            this.picDao = picDao;
        }

        @Override
        protected Void doInBackground(Pictures... pictures) {
            picDao.insert(Arrays.asList(pictures));
            return null;
        }
    }
}
