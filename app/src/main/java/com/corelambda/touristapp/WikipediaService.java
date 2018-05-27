package com.corelambda.touristapp;

import com.corelambda.touristapp.model.WikipediaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaService {
    @GET("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=pageimages&list=&generator=geosearch&piprop=thumbnail%7Cname%7Coriginal&pithumbsize=500&ggsradius=10000")
    Call<WikipediaResponse> getPlaces(@Query("ggscoord") String gpsCoord);
}
