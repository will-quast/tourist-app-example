package com.corelambda.touristapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.corelambda.touristapp.model.WikipediaPage;

import java.util.List;

public class PlacesViewModel extends ViewModel {

    private LiveData<List<WikipediaPage>>  placesListData;
    private PlacesRepository placesRepository;

    public PlacesViewModel(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
        placesListData = placesRepository.getPlacesList();
    }

    public LiveData<List<WikipediaPage>> getPlacesList() {
        return placesListData;
    }

    public static class PlacesViewModelFactory implements ViewModelProvider.Factory {

        private final PlacesRepository placesRepository;

        public PlacesViewModelFactory(PlacesRepository placesRepository) {
            this.placesRepository = placesRepository;
        }

        @Override
        public PlacesViewModel create(Class modelClass) {
            return new PlacesViewModel(placesRepository);
        }
    }
}
