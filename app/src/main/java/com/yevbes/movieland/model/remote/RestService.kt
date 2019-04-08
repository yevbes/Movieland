package com.yevbes.movieland.model.remote

import com.yevbes.movieland.model.res.ConfigurationRes
import com.yevbes.movieland.model.res.MoviesRes
import retrofit2.Call
import retrofit2.http.*

interface RestService {

    @GET("configuration")
    fun getConfiguration(
    ): Call<ConfigurationRes>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
    ): Call<MoviesRes>

    @GET("discover/movie")
    fun discoverMovie(
    ): Call<MoviesRes>
}
