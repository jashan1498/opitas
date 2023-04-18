package com.sample.opitas.utils

import android.os.Handler
import android.os.Looper

class SearchManager(private val searchFunction: (String) -> Unit) {

    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: SearchRunnable? = null

    companion object {
        private const val SEARCH_DELAY_MS = 1000L // 1 second delay

    }

    fun search(query: String) {
        if (query.length < 3) {
            // Don't start search if query is too short
            return
        }

        // Cancel any existing search runnable
        searchRunnable?.let {
            searchHandler.removeCallbacks(it)
        }

        // Create a new search runnable and post it to the handler
        searchRunnable = SearchRunnable(query).apply {
            searchHandler.postDelayed(this, SEARCH_DELAY_MS)
        }
    }

    private inner class SearchRunnable(private val query: String) : Runnable {

        override fun run() {
            searchFunction(query)
        }
    }
}