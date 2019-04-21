package com.yevbes.movieland.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.yevbes.movieland.service.Movie
import com.yevbes.movieland.service.MovieRepository

/**
 * View model for TopRatedMovieFragment
 */
class TopRatedMovieViewModel : ViewModel() {

    private var repository: MovieRepository = MovieRepository

    // LiveData
    private var allMovies: LiveData<ArrayList<Movie>>

    init {
        allMovies = repository.getMovies()

    }

    /**
     * Live data for movies
     */
    fun getAllMovies(): LiveData<ArrayList<Movie>> {
        return allMovies
    }

    override fun onCleared() {
        super.onCleared()
        repository.disposeCompositeDisposable()
    }
}