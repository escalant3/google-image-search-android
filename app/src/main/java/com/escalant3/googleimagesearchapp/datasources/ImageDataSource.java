package com.escalant3.googleimagesearchapp.datasources;


import android.content.Context;
import android.widget.ArrayAdapter;

import com.escalant3.googleimagesearchapp.adapters.ImagesArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageDataSource {

    public final static int PAGINATION_SIZE = 8;

    private int counter;
    private String query;
    private List<String> items;
    private ImagesArrayAdapter adapter;

    public ImageDataSource(Context context, List<String> items) {
        this.items = items;
        adapter = new ImagesArrayAdapter(context, items);
    }

    public ArrayAdapter getAdapter() {
        return adapter;
    }

    public void loadInitialData(final String query) {
        counter = 0;
        items.clear();

        this.query = query;

        // DELETE-ME Dummy data
        if (query != null) {
            addNMoreElements(PAGINATION_SIZE);
        }

        adapter.notifyDataSetChanged();
    }

    public void addNMoreElements(final int n) {
        for (int i = 0; i < n; i++) {
            items.add("http://lorempixel.com/300/300/" + query + "/" + counter);
            counter++;
        }
    }
}
