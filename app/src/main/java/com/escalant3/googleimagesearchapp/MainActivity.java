package com.escalant3.googleimagesearchapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.escalant3.googleimagesearchapp.adapters.ImagesArrayAdapter;
import com.escalant3.googleimagesearchapp.listeners.InfiniteScrollListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private int counter = 0;
    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.grid_view);

        // DELETE-ME Dummy data
        addNMoreElements(8);

        // Attach gridView with ImageAdapter
        gridView.setAdapter(new ImagesArrayAdapter(this, items));

        gridView.setOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void onLastItemShown() {
                onNewItemsLoadStart();
                addNMoreElements(8);
                onNewItemsLoadEnd();
            }
        });
    }

    private void addNMoreElements(int n) {
        for (int i = 0; i < n; i++) {
            items.add("http://lorempixel.com/300/300/sports/" + counter);
            counter++;
        }
    }
}
