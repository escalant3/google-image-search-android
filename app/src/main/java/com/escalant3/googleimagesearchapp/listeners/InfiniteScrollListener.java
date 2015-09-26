package com.escalant3.googleimagesearchapp.listeners;


import android.widget.AbsListView;

public abstract class InfiniteScrollListener implements AbsListView.OnScrollListener{

    private boolean loading = false;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastItemShown = firstVisibleItem + visibleItemCount;

        if (loading) {
            return;
        }

        if (visibleItemCount == totalItemCount) {
            return;
        }

        if (lastItemShown == totalItemCount) {
            onLastItemShown();
        }
    }

    public void onNewItemsLoadStart() {

        loading = true;
    }

    public void onNewItemsLoadEnd() {

        loading = false;
    }

    public void onNewItemsLoadError() {
        loading = false;
    }


    public abstract void onLastItemShown();
}
