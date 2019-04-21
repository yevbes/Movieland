package com.yevbes.movieland.service

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.yevbes.movieland.App
import com.yevbes.movieland.service.local.MovieDao
import com.yevbes.movieland.service.local.MovieDatabase
import com.yevbes.movieland.service.remote.api.RestService
import com.yevbes.movieland.service.remote.api.ServiceGenerator
import com.yevbes.movieland.service.remote.paging.MoviesSourceFactory
import com.yevbes.movieland.utils.ConstantManager
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

object MovieRepository {
    private var movieDao: MovieDao
    private val webservice: RestService
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var pagedListLiveData: LiveData<PagedList<Movie>>
    private var liveDataMerger = MediatorLiveData<List<Movie>>()


    init {
        val database = MovieDatabase.getInstance(App.getApplication())
        movieDao = database.movieDao()
        webservice = ServiceGenerator.createService(RestService::class.java)

        // DataSource
        val moviesSourceFactory = MoviesSourceFactory(compositeDisposable, webservice)

        // PagedList
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(ConstantManager.LOADING_PAGE_SIZE * 2)
            .setPageSize(ConstantManager.LOADING_PAGE_SIZE)
            .build()

        pagedListLiveData = LivePagedListBuilder<Int, Movie>(moviesSourceFactory, config)
            .build()
//        refreshData()
    }

    fun disposeCompositeDisposable() {
        compositeDisposable.dispose()
    }

    fun getPagedListLiveData(): LiveData<PagedList<Movie>> {
        return pagedListLiveData
    }

    /*fun getMovies(): LiveData<List<Movie>> {
        return movieDao.getAllMovies()
    }*/

    fun insertAll(movies: List<Movie>) {
        Completable.fromAction {
            movieDao.insertAll(movies)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    Timber.tag("OK ->").v("OK on insert movies to DB")
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Timber.tag("Error ->").e("Error on insert movies to DB")
                }
            })
    }
}