package com.corelambda.touristapp;

import com.corelambda.touristapp.model.QueryResponse;
import com.corelambda.touristapp.model.WikipediaPage;
import com.corelambda.touristapp.model.WikipediaResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.mock.Calls;

public class MockWikipediaService implements WikipediaService {

    @Override
    public Call<WikipediaResponse> getPlaces() {

        // create some mock data to test with
        WikipediaResponse wikipediaResponse = new WikipediaResponse();
        wikipediaResponse.setQuery(new QueryResponse());
        wikipediaResponse.getQuery().setPages(new HashMap<Integer, WikipediaPage>());
        wikipediaResponse.getQuery().getPages().put(1234, new WikipediaPage());
        wikipediaResponse.getQuery().getPages().get(1234).setTitle("Statue");

        // magic tool that creates a mock Call that returns the mock data created above
        return Calls.response(wikipediaResponse);
    }
}
