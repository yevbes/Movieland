package com.yevbes.movieland.service

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.yevbes.movieland.App
import com.yevbes.movieland.service.local.MovieDao
import com.yevbes.movieland.service.local.MovieDatabase
import com.yevbes.movieland.service.remote.State
import com.yevbes.movieland.service.remote.api.RestService
import com.yevbes.movieland.service.remote.api.ServiceGenerator
import com.yevbes.movieland.service.remote.paging.MovieDataSource
import com.yevbes.movieland.service.remote.paging.MovieDataSourceFactory
import com.yevbes.movieland.utils.ConstantManager
import io.reactivex.disposables.CompositeDisposable

object MovieRepository {
    private var movieDao: MovieDao
    private val webservice: RestService
    var moviesRes: LiveData<PagedList<Movie>>
    private val movieDataSourceFactory: MovieDataSourceFactory
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    init {
        val database = MovieDatabase.getInstance(App.getApplication())
        movieDao = database.movieDao()
        webservice = ServiceGenerator.createService(RestService::class.java)

//        allMovies = movieDao.getAllMovies()

        movieDataSourceFactory = MovieDataSourceFactory(compositeDisposable)

        val config = PagedList.Config.Builder()
            .setPageSize(ConstantManager.LOADING_PAGE_SIZE)
            .setInitialLoadSizeHint(ConstantManager.LOADING_PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()
        moviesRes = LivePagedListBuilder<Int, Movie>(movieDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<MovieDataSource,
            State>(movieDataSourceFactory.liveData, MovieDataSource::state)

    fun retry() {
        movieDataSourceFactory.liveData.value?.retry()
    }

    fun invalidate(){
        movieDataSourceFactory.liveData.value?.invalidate()
    }

    fun listIsEmpty(): Boolean {
        return moviesRes.value?.isEmpty() ?: true
    }

    fun disposeCompositeDisposable() {
        compositeDisposable.dispose()
    }

    fun insert(movie: Movie) {
        movieDao.insert(movie)
    }

    fun update(movie: Movie) {
        movieDao.update(movie)
    }

    fun delete(movie: Movie) {
        movieDao.delete(movie)
    }

    fun deleteAllMovies() {
        movieDao.deleteAllMovies()
    }
    
}