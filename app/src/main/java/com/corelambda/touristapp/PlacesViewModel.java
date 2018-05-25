package com.corelambda.touristapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

public class PlacesViewModel extends ViewModel {

    private static final List<String> placesList = Arrays.asList("Statue", "Scenic Overlook", "Art Museum",
            "Market", "Museum of Natural History", "Temple", "Amusement Park", "City Hall", "Big Bridge");
    private LiveData<List<String>>  placesListData;

    public PlacesViewModel() {
        final MutableLiveData<List<String>> data = new MutableLiveData<>();
        data.setValue(placesList);
        placesListData = data;

    }

    public LiveData<List<String>> getPlacesList() {
        return placesListData;
    }
}
