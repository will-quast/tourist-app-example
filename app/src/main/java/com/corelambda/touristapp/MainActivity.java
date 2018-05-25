package com.corelambda.touristapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;

    private PlacesViewModel placesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        placesViewModel = ViewModelProviders.of(this).get(PlacesViewModel.class);
        placesViewModel.getPlacesList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> places) {
                recyclerAdapter = new TouristRecyclerAdapter(places);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });
    }
}
