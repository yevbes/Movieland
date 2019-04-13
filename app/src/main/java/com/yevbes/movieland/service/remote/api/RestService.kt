package com.yevbes.movieland.service.remote.api

import com.yevbes.movieland.service.Movie
import com.yevbes.movieland.service.remote.model.res.ConfigurationRes
import com.yevbes.movieland.utils.AppConfig.Companion.LANGUAGE_REQUEST_PARAM
import com.yevbes.movieland.utils.AppConfig.Companion.PAGE_REQUEST_PARAM
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestService {

    @GET("configuration")
    fun getConfiguration(
    ): Single<ConfigurationRes>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query(LANGUAGE_REQUEST_PARAM) language: String,
        @Query(PAGE_REQUEST_PARAM) page: Int
    ): Single<ArrayList<Movie>>


    @GET("discover/movie")
    fun discoverMovie(
    ): Single<ArrayList<Movie>>
}
