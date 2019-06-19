package com.example.webservicewitharch.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.webservicewitharch.PicsRepository;
import com.example.webservicewitharch.models.Pictures;

import java.util.List;

public class PicsViewModel extends AndroidViewModel {

    private PicsRepository repository;
    private LiveData<List<Pictures>> allpics;

    public PicsViewModel(@NonNull Application application) {
        super(application);
        repository = new PicsRepository(application);
        allpics = repository.getAllPictures();
    }

    public void insert(Pictures pictures) {
        repository.insert(pictures);
    }

    public LiveData<List<Pictures>> getAllPictures() {
        return allpics;
    }
}
