package com.escalant3.googleimagesearchapp.services;


import com.escalant3.googleimagesearchapp.models.GoogleImagesResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface GoogleImageSearchService {

    @GET("/ajax/services/search/images?rsz=8&v=1.0")
    void listImages(@Query("q") String query, @Query("start") int start, Callback<GoogleImagesResponse> response);
}
