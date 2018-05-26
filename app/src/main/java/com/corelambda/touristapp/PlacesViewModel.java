package com.corelambda.touristapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class PlacesViewModel extends ViewModel {

    private LiveData<List<String>>  placesListData;
    private PlacesRepository placesRepository;

    public PlacesViewModel() {
        placesRepository = new PlacesRepository();
        placesListData = placesRepository.getPlacesList();
    }

    public LiveData<List<String>> getPlacesList() {
        return placesListData;
    }
}
