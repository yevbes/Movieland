package com.yevbes.movieland.service.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import com.yevbes.movieland.service.MovieDatabase
import com.yevbes.movieland.service.model.dao.MovieDao
import com.yevbes.movieland.service.model.entities.Movie
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieRepository(
    application: Application
) {
    private var movieDao: MovieDao
    private var allMovies: LiveData<List<Movie>>

    init {
        val database = MovieDatabase.getInstance(application)
        movieDao = database.movieDao()
        allMovies = movieDao.getAllMovies()
    }

    fun insert(movie: Movie) {
        Completable.fromAction {
            movieDao.insert(movie)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {

                }

            })
    }

    fun update(movie: Movie) {
        Completable.fromAction {
            movieDao.update(movie)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver{
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {

                }

            })
    }
    fun delete(movie: Movie) {
        Completable.fromAction {
            movieDao.delete(movie)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver{
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {

                }

            })
    }

    fun deleteAllMovies() {
        Completable.fromAction {
            movieDao.deleteAllMovies()
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver{
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {

                }

            })
    }

    fun getAllMovies(): LiveData<List<Movie>> {
        return allMovies
    }
}