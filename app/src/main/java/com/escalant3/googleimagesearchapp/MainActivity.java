package com.escalant3.googleimagesearchapp;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.GridView;

import com.escalant3.googleimagesearchapp.datasources.ImageDataSource;
import com.escalant3.googleimagesearchapp.listeners.InfiniteScrollListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private GridView gridView;
    private ImageDataSource dataSource;

    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new ImageDataSource(this, items);

        loadSearchWidget();
        loadGridView();
    }

    private void loadSearchWidget() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                dataSource.loadInitialData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dataSource.loadInitialData(null);
                return false;
            }
        });
    }

    private void loadGridView() {
        gridView = (GridView) findViewById(R.id.grid_view);

        // Attach gridView with ImageAdapter
        gridView.setAdapter(dataSource.getAdapter());

        gridView.setOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void onLastItemShown() {
                onNewItemsLoadStart();
                dataSource.addNMoreElements(ImageDataSource.PAGINATION_SIZE);
                onNewItemsLoadEnd();
            }
        });
    }
}
