package com.yevbes.movieland.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.databinding.ObservableBoolean
import com.yevbes.movieland.service.MovieRepository
import com.yevbes.movieland.service.remote.State
import com.yevbes.movieland.service.remote.model.res.MoviesRes


class TopRatedMovieViewModel : ViewModel() {

    private var repository: MovieRepository = MovieRepository
    var isLoading = ObservableBoolean()
    private var isLoadingLive: MutableLiveData<Boolean>
    // LiveData
    private var allMovies: LiveData<PagedList<MoviesRes.Result>>


    init {
        allMovies = repository.moviesRes
        isLoadingLive = MutableLiveData()
    }

    fun retry() {
        return repository.retry()
    }

    fun isLoadingLive(): LiveData<Boolean> {
        return isLoadingLive
    }

    // SwipeRefreshLayout
    fun onRefresh() {
        isLoading.set(true)
        isLoadingLive.value = isLoading.get()
        repository.invalidate()
    }

    fun onReady() {
        isLoadingLive.value = false
        isLoading.set(isLoadingLive.value!!)
    }

    fun onError() {
        isLoadingLive.value = false
        isLoading.set(isLoadingLive.value!!)
    }

    fun getAllMovies(): LiveData<PagedList<MoviesRes.Result>> {
        return allMovies
    }

    override fun onCleared() {
        super.onCleared()
        repository.disposeCompositeDisposable()
    }

    fun listIsEmpty(): Boolean {
        return repository.listIsEmpty()
    }

    fun getState(): LiveData<State> {
        return repository.getState()
    }
}