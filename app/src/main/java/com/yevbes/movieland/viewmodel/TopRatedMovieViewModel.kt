package com.yevbes.movieland.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.yevbes.movieland.service.MovieRepository
import com.yevbes.movieland.service.remote.model.res.MoviesRes
import io.reactivex.disposables.CompositeDisposable


class TopRatedMovieViewModel: ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var repository: MovieRepository = MovieRepository(compositeDisposable)

    // LiveData
    private var allMovies: LiveData<PagedList<MoviesRes.Result>>

    init {
        allMovies = repository.moviesRes
    }

    fun getAllMovies() : LiveData<PagedList<MoviesRes.Result>>{
        return allMovies
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}