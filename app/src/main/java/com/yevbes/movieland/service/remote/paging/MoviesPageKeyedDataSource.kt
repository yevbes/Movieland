package com.yevbes.movieland.service.remote.paging

import android.arch.paging.PageKeyedDataSource
import com.yevbes.movieland.service.Movie
import com.yevbes.movieland.service.remote.api.RestService
import com.yevbes.movieland.utils.AppConfig
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MoviesPageKeyedDataSource(
    private val webservice: RestService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        webservice.getTopRatedMovies(AppConfig.LANGUAGE, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ArrayList<Movie>> {
                override fun onSuccess(t: ArrayList<Movie>) {
//                    moviesLiveData.value = t
                    callback.onResult(t, null, 2)
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Timber.tag("Error ->").e(e)
                }
            }
            )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        webservice.getTopRatedMovies(AppConfig.LANGUAGE, params.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ArrayList<Movie>> {
                override fun onSuccess(t: ArrayList<Movie>) {
//                    moviesLiveData.value = t
                    callback.onResult(t, params.key + 1)
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {

                }
            }
            )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}