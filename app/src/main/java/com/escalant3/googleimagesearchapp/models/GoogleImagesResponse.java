package com.escalant3.googleimagesearchapp.models;


import java.util.ArrayList;

public class GoogleImagesResponse {

    private ArrayList<String> results;

    public GoogleImagesResponse(ArrayList<String> results) {
        this.results = results;
    }

    public ArrayList<String> getResults() {
        return results;
    }
}
