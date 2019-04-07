package com.yevbes.movieland.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.yevbes.movieland.model.res.MoviesRes
import com.yevbes.movieland.repository.MovieRepository


class TopRatedMovieViewModel: ViewModel() {

    private var repository: MovieRepository = MovieRepository

    // LiveData
    private var allMovies: LiveData<List<MoviesRes.Result>>

    init {
        allMovies = repository.getAllMovies()
    }

    fun getAllMovies() : LiveData<List<MoviesRes.Result>>{
        return allMovies
    }

}