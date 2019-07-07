package com.example.webservicewitharch.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webservicewitharch.R;

import static com.example.webservicewitharch.ui.PicsViewModel.EXTRA_CREATOR;
import static com.example.webservicewitharch.ui.PicsViewModel.EXTRA_LIKES;
import static com.example.webservicewitharch.ui.PicsViewModel.EXTRA_URL;


public class MainActivity extends AppCompatActivity {
    PicsViewModel picsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        PicsAdapter adapter = new PicsAdapter();
        recyclerView.setAdapter(adapter);

        picsViewModel = ViewModelProviders.of(this).get(PicsViewModel.class);
        picsViewModel.getAllPictures().observe(this, adapter::setPics);


        adapter.setOnItemClickListener(pictures -> {

            Intent detailIntent = new Intent(this, DetailActivity.class);

            detailIntent.putExtra(EXTRA_URL, pictures.getmImageUrl());
            detailIntent.putExtra(EXTRA_CREATOR, pictures.getmCreator());
            detailIntent.putExtra(EXTRA_LIKES, pictures.getmLikes());

            startActivity(detailIntent);

        });


    }
}