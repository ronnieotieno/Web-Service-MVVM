package com.example.webservicewitharch.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webservicewitharch.R;


public class MainActivity extends AppCompatActivity {
    PicsViewModel picsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final PicsAdapter adapter = new PicsAdapter();
        recyclerView.setAdapter(adapter);

        picsViewModel = ViewModelProviders.of(this).get(PicsViewModel.class);
        picsViewModel.getAllPictures().observe(this, adapter::setPics);
    }
}