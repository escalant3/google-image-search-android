package com.escalant3.googleimagesearchapp.contentproviders;

import android.content.SearchRecentSuggestionsProvider;

public class GoogleImagesSearchProvider extends SearchRecentSuggestionsProvider {
    public final static int MODE = DATABASE_MODE_QUERIES;
    public final static String AUTHORITY =
            "com.escalant3.GoogleImagesSearchProvider";

    public GoogleImagesSearchProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
