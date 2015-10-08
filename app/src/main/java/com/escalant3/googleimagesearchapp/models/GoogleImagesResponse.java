package com.escalant3.googleimagesearchapp.models;


import java.util.List;

public class GoogleImagesResponse {

    private GoogleImagesResponseData responseData;

    public List<GoogleImage> getResults() {
        return responseData.results;
    }

    private class GoogleImagesResponseData {
        List<GoogleImage> results;
    }
}
