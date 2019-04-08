package com.yevbes.movieland.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.yevbes.movieland.App
import com.yevbes.movieland.model.db.Movie
import com.yevbes.movieland.model.res.MoviesRes
import com.yevbes.movieland.model.local.MovieDao
import com.yevbes.movieland.model.local.MovieDatabase
import com.yevbes.movieland.model.remote.RestService
import com.yevbes.movieland.model.remote.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MovieRepository {
    private var movieDao: MovieDao
    private val webservice = ServiceGenerator.createService(RestService::class.java)

    init {
        val database = MovieDatabase.getInstance(App.getApplication())
        movieDao = database.movieDao()
//        allMovies = movieDao.getAllMovies()
    }

    fun getAllMovies() : LiveData<List<MoviesRes.Result>>{
        val mutableLiveData = MutableLiveData<List<MoviesRes.Result>>()
        webservice.getTopRatedMovies().enqueue(
            object : Callback<MoviesRes> {
                override fun onFailure(call: Call<MoviesRes>, t: Throwable) {

                }

                override fun onResponse(call: Call<MoviesRes>, response: Response<MoviesRes>) {
                    val list = ArrayList<MoviesRes.Result>()
                    list.addAll(response.body()!!.results)
                    mutableLiveData.value = list
                }

            }
        )
        return mutableLiveData
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