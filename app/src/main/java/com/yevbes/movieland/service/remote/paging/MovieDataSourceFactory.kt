package com.yevbes.movieland.service.remote.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource

import com.yevbes.movieland.service.remote.model.res.MoviesRes
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MoviesRes.Result>() {

    val liveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, MoviesRes.Result> {
        val movieDataSource = MovieDataSource(compositeDisposable)
        liveData.postValue(movieDataSource)
        return movieDataSource
    }

}