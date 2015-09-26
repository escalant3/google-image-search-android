package com.escalant3.googleimagesearchapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.escalant3.googleimagesearchapp.adapters.ImagesArrayAdapter;
import com.escalant3.googleimagesearchapp.listeners.InfiniteScrollListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private GridView gridView;

    private int counter = 0;
    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadSearchWidget();

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchView.setQueryHint("Showing results for: " + query);
            initGridViewForQuery(query);
        }
    }

    private void loadSearchWidget() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                initGridViewForQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initGridViewForQuery(final String query) {
        counter = 0;
        items.clear();

        gridView = (GridView) findViewById(R.id.grid_view);

        // DELETE-ME Dummy data
        addNMoreElements(query, 8);

        // Attach gridView with ImageAdapter
        gridView.setAdapter(new ImagesArrayAdapter(this, items));

        gridView.setOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void onLastItemShown() {
                onNewItemsLoadStart();
                addNMoreElements(query, 8);
                onNewItemsLoadEnd();
            }
        });
    }

    private void addNMoreElements(String query, int n) {
        for (int i = 0; i < n; i++) {
            items.add("http://lorempixel.com/300/300/" + query + "/" + counter);
            counter++;
        }
    }
}
