package com.corelambda.touristapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PlacesRepository {


    public LiveData<List<String>> getPlacesList() {
        final MutableLiveData<List<String>> liveData = new MutableLiveData<>();

        /*
         TODO: Android Studio will warn us that this use of AsyncTask could be a memory leak, therefore
           we want to find a better way to do this.
        */
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    // get the response to the Wikipedia query as a String
                    String response = doRequest();

                    // write the response to the console so I can read it
                    Log.d("PlacesViewModel", doRequest());

                    // update the LiveData with the Wikipedia response
                    handleResponse(response, liveData);

                } catch (Exception e) {
                    Log.e("PlacesViewModel", "REQUEST FAILED! " + e);

                    // TODO: we should handle errors better than this
                    liveData.postValue(Arrays.asList("Error"));
                }
                return null;
            }
        }.execute();
        return liveData;
    }

    private String doRequest() throws IOException {
        // the URL of the Wikipedia data we want to display
        String urlString = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=pageimages&list=&generator=geosearch&piprop=thumbnail%7Cname%7Coriginal&pithumbsize=500&ggsradius=10000&ggscoord=10.7712404%7C106.6978887";

        // storage for the data as we read it in
        StringBuilder response = new StringBuilder();

        // open a HTTP connection to Wikipedia
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            // convert the response to a Reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            // read each line one at a time until the response is empty
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            // closes the HTTP connection to Wikipedia
            urlConnection.disconnect();
        }

        return response.toString();
    }

    private void handleResponse(String responseString, MutableLiveData<List<String>> liveData) throws JSONException {
        /*
               Example response:
               {
                 "query": {
                   "pages": {
                     "1234567": {
                       "title": "Big Bridge",
                       ...
                     },
                     ...
                   }
                 }
               }
         */

        List<String> placeNames = new ArrayList<>();

        JSONObject responseObject = new JSONObject(responseString);
        JSONObject queryObject = responseObject.getJSONObject("query");
        JSONObject pagesObject = queryObject.getJSONObject("pages");

        // to visit each page we need must iterate over keys and reference it by its key
        Iterator<String> keys = pagesObject.keys();
        while (keys.hasNext()) {
            JSONObject page = pagesObject.getJSONObject(keys.next());
            placeNames.add(page.getString("title"));
        }

        // update all observers that the new data is ready
        liveData.postValue(placeNames);
    }
}
