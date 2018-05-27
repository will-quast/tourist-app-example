package com.corelambda.touristapp;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

import com.corelambda.touristapp.model.WikipediaPage;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;


public class PlacesViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void test_getPlacesList() {
        // create a WikipidiaService that is instructed to respond with mock data
        WikipediaService mockWikipediaService = new MockWikipediaService();

        // create a REAL PlacesViewModel to perform the test on
        PlacesRepository placesRepository = new PlacesRepository(mockWikipediaService); // <-- real repository with a mock service!
        PlacesViewModel.PlacesViewModelFactory factory = new PlacesViewModel.PlacesViewModelFactory(placesRepository);
        PlacesViewModel model = factory.create(PlacesViewModel.class);

        // perform the testable action
        LiveData<List<WikipediaPage>> placesLiveData = model.getPlacesList();
        // knowing "Statue" is at position one mock data, assert tha the code gave us that in the LiveData
        Assert.assertEquals("Statue", placesLiveData.getValue().get(0).getTitle());
    }
}