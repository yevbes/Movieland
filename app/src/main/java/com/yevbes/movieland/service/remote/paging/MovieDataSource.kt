package com.yevbes.movieland.service.remote.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.yevbes.movieland.service.Movie
import com.yevbes.movieland.service.remote.State
import com.yevbes.movieland.service.remote.api.RestService
import com.yevbes.movieland.service.remote.api.ServiceGenerator
import com.yevbes.movieland.utils.AppConfig
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
    private val compositeDisposable: CompositeDisposable
): PageKeyedDataSource<Int, Movie>() {

    var state: MutableLiveData<State> = MutableLiveData()

    // Completable for emit onError or onComplete
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            ServiceGenerator.createService(RestService::class.java)
                .getTopRatedMovies(AppConfig.LANGUAGE,1)
                .subscribe(
                    {
                        response ->
                            updateState(State.DONE)
                            callback.onResult(response, null, 2)
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action {
                            loadInitial(params,callback)
                        })
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            ServiceGenerator.createService(RestService::class.java)
                .getTopRatedMovies(AppConfig.LANGUAGE, params.key)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(response,
                            params.key + 1
                        )
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}