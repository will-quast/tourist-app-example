package com.corelambda.touristapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.corelambda.touristapp.model.WikipediaPage;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        placesViewModel = ViewModelProviders.of(this, createViewModelFactory()).get(PlacesViewModel.class);
        placesViewModel.getPlacesList().observe(this, new Observer<List<WikipediaPage>>() {
            @Override
            public void onChanged(@Nullable List<WikipediaPage> places) {
                recyclerAdapter = new TouristRecyclerAdapter(places);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });
    }

    private PlacesViewModel.PlacesViewModelFactory createViewModelFactory() {

        // gather all the dependencies for the PlacesViewModelFactory
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://en.wikipedia.org")
                .build();
        PlacesRepository placesRepository = new PlacesRepository(retrofit.create(WikipediaService.class));

        // return a factory that can create PlacesViewModels on demand
        return new PlacesViewModel.PlacesViewModelFactory(placesRepository);
    }
}
