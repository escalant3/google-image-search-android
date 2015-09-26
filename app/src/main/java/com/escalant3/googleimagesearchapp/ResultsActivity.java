package com.escalant3.googleimagesearchapp;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.escalant3.googleimagesearchapp.datasources.ImageDataSource;
import com.escalant3.googleimagesearchapp.listeners.InfiniteScrollListener;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private GridView gridView;
    private Toolbar toolbar;

    private ImageDataSource dataSource;

    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        dataSource = new ImageDataSource(this, items);

        loadGridView();

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            configureToolbar(query);
            dataSource.loadInitialData(query);
        }
    }

    private void configureToolbar(String query) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(String.format(getString(R.string.results_title), query));
    }

    private void loadGridView() {
        gridView = (GridView) findViewById(R.id.grid_view);

        // Attach gridView with ImageAdapter
        gridView.setAdapter(dataSource.getAdapter());

        gridView.setOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void onLastItemShown() {
                dataSource.addNMoreElements(ImageDataSource.PAGINATION_SIZE);
            }
        });
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
}
