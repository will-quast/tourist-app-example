package com.corelambda.touristapp;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;

import static org.junit.Assert.assertTrue;


public class PlacesViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void getPlacesList() {
        PlacesViewModel model = new PlacesViewModel();
        LiveData<List<String>> colorsData = model.getPlacesList();
        assertTrue(colorsData.getValue().get(0).equals("Statue"));
    }
}