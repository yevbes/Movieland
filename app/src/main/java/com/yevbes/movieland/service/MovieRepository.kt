package com.yevbes.movieland.service

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.yevbes.movieland.App
import com.yevbes.movieland.service.local.MovieDao
import com.yevbes.movieland.service.local.MovieDatabase
import com.yevbes.movieland.service.remote.api.RestService
import com.yevbes.movieland.service.remote.api.ServiceGenerator
import com.yevbes.movieland.utils.AppConfig
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

object MovieRepository {
    private var movieDao: MovieDao
    private val webservice: RestService
    private val moviesLiveData = MutableLiveData<ArrayList<Movie>>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        val database = MovieDatabase.getInstance(App.getApplication())
        movieDao = database.movieDao()

        webservice = ServiceGenerator.createService(RestService::class.java)
        webservice.getTopRatedMovies(AppConfig.LANGUAGE, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ArrayList<Movie>> {
                override fun onSuccess(t: ArrayList<Movie>) {
                    moviesLiveData.value = t
                    insertAll(t)
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                }

            }
            )
    }

    fun getMovies(): LiveData<ArrayList<Movie>> {
        return moviesLiveData
    }

    fun disposeCompositeDisposable() {
        compositeDisposable.dispose()
    }

    fun insertAll(movies: List<Movie>) {

        Completable.fromAction{
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

    fun insert(movie: Movie) {
        Completable.fromAction {
            movieDao.insert(movie)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                }
            })
    }

    fun update(movie: Movie) {
        Completable.fromAction {
            movieDao.update(movie)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                }
            })
    }

    fun delete(movie: Movie) {
        Completable.fromAction {
            movieDao.delete(movie)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                }
            })
    }

    fun deleteAllMovies() {
        Completable.fromAction {
            movieDao.deleteAllMovies()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                }
            })
    }
}