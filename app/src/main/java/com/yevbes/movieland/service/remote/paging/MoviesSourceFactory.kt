package com.yevbes.movieland.service.remote.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.yevbes.movieland.service.Movie
import com.yevbes.movieland.service.remote.api.RestService
import io.reactivex.disposables.CompositeDisposable

class MoviesSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val webservice: RestService
) : DataSource.Factory<Int, Movie>() {

    private val moviesDataSourceLiveData = MutableLiveData<MoviesPageKeyedDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val moviesDataSource = MoviesPageKeyedDataSource(webservice,compositeDisposable)
        moviesDataSourceLiveData.postValue(moviesDataSource)
        return moviesDataSource
    }

    fun getMoviesDataSourceLiveData(): MutableLiveData<MoviesPageKeyedDataSource> {
        return moviesDataSourceLiveData
    }

}