package com.corelambda.touristapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.corelambda.touristapp.model.WikipediaPage;
import com.corelambda.touristapp.model.WikipediaResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesRepository {
    private WikipediaService service;

    private static final String gpsLocation = "10.7712404|106.6978887";

    public PlacesRepository(WikipediaService service) {
        this.service = service;
    }

    public LiveData<List<WikipediaPage>> getPlacesList() {
        final MutableLiveData<List<WikipediaPage>> liveData = new MutableLiveData<>();
        service.getPlaces(gpsLocation).enqueue(new Callback<WikipediaResponse>() {
            @Override
            public void onResponse(Call<WikipediaResponse> call, Response<WikipediaResponse> response) {
                if (response != null
                        && response.body() != null
                        && response.body().getQuery() != null
                        && response.body().getQuery().getPages() != null) {
                    Map<Integer, WikipediaPage> pagesMap = response.body().getQuery().getPages();
                    List<WikipediaPage> pages = new ArrayList<>(pagesMap.values());
                    liveData.postValue(pages);
                } else {
                    Log.e("PlacesViewModel", "Response has a null value");
                    // TODO: we should handle errors better than this
                }

            }

            @Override
            public void onFailure(Call<WikipediaResponse> call, Throwable t) {
                Log.e("PlacesViewModel", "REQUEST FAILED! ", t);
                // TODO: we should handle errors better than this
            }
        });

        return liveData;
    }
}
