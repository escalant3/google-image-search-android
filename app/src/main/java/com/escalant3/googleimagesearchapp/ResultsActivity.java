package com.escalant3.googleimagesearchapp;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.GridView;

import com.escalant3.googleimagesearchapp.datasources.ImageDataSource;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private GridView gridView;
    private Toolbar toolbar;

    private ImageDataSource dataSource;

    private static final String RESULTS_STRING_ARRAY_KEY = "results_array";
    private static final String QUERY_STRING_KEY = "query";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        dataSource = new ImageDataSource(this);

        loadGridView();

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            if (savedInstanceState == null) {
                dataSource.loadInitialData(intent.getStringExtra(SearchManager.QUERY));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        configureToolbar(dataSource.getQuery());
    }

    private void configureToolbar(String query) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(String.format(getString(R.string.results_title), query));
    }

    private void loadGridView() {
        gridView = (GridView) findViewById(R.id.grid_view);

        // Attach gridView to ImageAdapter
        gridView.setAdapter(dataSource.getAdapter());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putStringArrayList(RESULTS_STRING_ARRAY_KEY, dataSource.getItems());
        savedInstanceState.putString(QUERY_STRING_KEY, dataSource.getQuery());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<String> items = savedInstanceState.getStringArrayList(RESULTS_STRING_ARRAY_KEY);
        String query = savedInstanceState.getString(QUERY_STRING_KEY);
        dataSource.loadWithPreloadedData(query, items);
    }
}
