package com.yevbes.movieland.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.yevbes.movieland.service.Movie
import com.yevbes.movieland.service.MovieRepository

/**
 * View model for TopRatedMovieFragment
 */
class TopRatedMovieViewModel : ViewModel() {

    private var repository: MovieRepository = MovieRepository

    // LiveData
//    private var allMovies: LiveData<List<Movie>>

    private var allPagedListLiveData: LiveData<PagedList<Movie>>

    init {
//        allMovies = repository.getMovies()
        allPagedListLiveData = repository.getPagedListLiveData()
    }

    /**
     * Live data for movies
     */
//    fun getAllMovies(): LiveData<List<Movie>> {
//        return allMovies
//    }

    fun getPagedListLiveData(): LiveData<PagedList<Movie>> {
        return allPagedListLiveData
    }

    override fun onCleared() {
        super.onCleared()
        repository.disposeCompositeDisposable()
    }
}