package com.yevbes.movieland.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.databinding.ObservableBoolean
import com.yevbes.movieland.service.Movie
import com.yevbes.movieland.service.MovieRepository
import com.yevbes.movieland.service.remote.State

/**
 * View model for TopRatedMovieFragment
 */
class TopRatedMovieViewModel : ViewModel() {

    private var repository: MovieRepository = MovieRepository
    var isLoading = ObservableBoolean()
    private var isLoadingLive: MutableLiveData<Boolean>
    // LiveData
    private var allMovies: LiveData<PagedList<Movie>>

    init {
        allMovies = repository.moviesRes
        isLoadingLive = MutableLiveData()
    }

    /**
     * Retry load data onError
     */
    fun retry() {
        return repository.retry()
    }

    /**
     * Save state of loading in Swipe Refresh
     */
    fun isLoadingLive(): LiveData<Boolean> {
        return isLoadingLive
    }

    /**
     * OnRefresh invalidate data
     */
    fun onRefresh() {
        isLoading.set(true)
        isLoadingLive.value = isLoading.get()
        repository.invalidate()
    }

    /**
     * On Data is loaded
     */
    fun onReady() {
        isLoadingLive.value = false
        isLoading.set(isLoadingLive.value!!)
    }

    /**
     * On Error
     */
    fun onError() {
        isLoadingLive.value = false
        isLoading.set(isLoadingLive.value!!)
    }

    /**
     * Live data for movies
     */
    fun getAllMovies(): LiveData<PagedList<Movie>> {
        return allMovies
    }

    /**
     * Clearing subscriptions
     */
    override fun onCleared() {
        super.onCleared()
        repository.disposeCompositeDisposable()
    }

    /**
     * Check for empty list
     */
    fun listIsEmpty(): Boolean {
        return repository.listIsEmpty()
    }

    /**
     * Getting current state
     */
    fun getState(): LiveData<State> {
        return repository.getState()
    }
}