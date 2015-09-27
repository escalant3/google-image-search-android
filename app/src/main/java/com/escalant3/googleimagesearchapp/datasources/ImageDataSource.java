package com.escalant3.googleimagesearchapp.datasources;


import android.content.Context;
import android.provider.SearchRecentSuggestions;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.escalant3.googleimagesearchapp.R;
import com.escalant3.googleimagesearchapp.adapters.ImagesArrayAdapter;
import com.escalant3.googleimagesearchapp.contentproviders.GoogleImagesSearchProvider;
import com.escalant3.googleimagesearchapp.deserializers.GoogleImagesDeserializer;
import com.escalant3.googleimagesearchapp.models.GoogleImagesResponse;
import com.escalant3.googleimagesearchapp.services.GoogleImageSearchService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class ImageDataSource {

    public final static int PAGINATION_SIZE = 8;
    private final static int MAX_GOOGLE_IMAGES_RESULTS = 64;

    private int counter;
    private boolean loading = false;
    private boolean noMoreDataToShow = false;

    private String query;

    private Context context;
    private List<String> items;
    private ImagesArrayAdapter adapter;
    private GoogleImageSearchService service;

    public ImageDataSource(Context context, List<String> items) {
        this.items = items;
        this.context = context;
        adapter = new ImagesArrayAdapter(context, items) {
            @Override
            public void onLastElementVisible() {
                addNMoreElements(ImageDataSource.PAGINATION_SIZE);
            }
        };
        service = getGoogleImageSearchService();
    }

    public ArrayAdapter getAdapter() {
        return adapter;
    }

    public void loadInitialData(final String query) {
        counter = 0;
        items.clear();

        this.query = query;

        if (query != null) {
            addNMoreElements(PAGINATION_SIZE);
        }

        adapter.notifyDataSetChanged();
    }

    public void addNMoreElements(final int n) {
        if (loading || noMoreDataToShow || counter >= MAX_GOOGLE_IMAGES_RESULTS) {
            return;
        }

        // Add query to recent searches
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this.context,
                GoogleImagesSearchProvider.AUTHORITY, GoogleImagesSearchProvider.MODE);
        suggestions.saveRecentQuery(query, null);

        loading = true;

        service.listImages(query, counter, new Callback<GoogleImagesResponse>() {
            @Override
            public void success(GoogleImagesResponse googleImagesResponse, Response response) {
                int currentCounter = counter;

                for (String imageUrl : googleImagesResponse.getResults()) {
                    items.add(imageUrl);
                    counter++;
                }

                if (currentCounter == counter) {
                    noMoreDataToShow = true;
                }

                adapter.notifyDataSetChanged();
                loading = false;
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, context.getString(R.string.error_google_images_connection), Toast.LENGTH_SHORT);
                loading = false;
            }
        });
    }

    private GoogleImageSearchService getGoogleImageSearchService() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GoogleImagesResponse.class, new GoogleImagesDeserializer())
                .create();

        RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint(context.getString(R.string.google_images_endpoint))
                .setConverter(new GsonConverter(gson))
                .build();

        return retrofit.create(GoogleImageSearchService.class);
    }
}
