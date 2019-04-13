package com.yevbes.movieland.service.remote.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.yevbes.movieland.service.Movie

import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val liveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(compositeDisposable)
        liveData.postValue(movieDataSource)
        return movieDataSource
    }

}