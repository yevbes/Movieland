package com.yevbes.movieland.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.yevbes.movieland.model.res.MoviesRes
import com.yevbes.movieland.model.MovieRepository
import android.R
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yevbes.movieland.App


class TopRatedMovieViewModel: ViewModel() {

    companion object {
        @BindingAdapter("bind:imageUrl")
        fun loadImage(view: ImageView, imageUrl: String) {
            val imgUrl =
            Glide.with(App.getApplication())
                .load(imageUrl)
//            .placeholder(lottieAnimationView.drawable)
                .into(view)
        }
    }

    private var repository: MovieRepository = MovieRepository

    // LiveData
    private var allMovies: LiveData<List<MoviesRes.Result>>

    init {
        allMovies = repository.getAllMovies()
    }

    fun getAllMovies() : LiveData<List<MoviesRes.Result>>{
        return allMovies
    }

    fun getImageUrl(): String {
        // The URL will usually come from a model (i.e Profile)
        //return AppConfig.BASE_IMAGE_URL + AppConfig.IMAGE_SIZE + currentMovie.posterPath
        return AppConfig.BASE_IMAGE_URL + AppConfig.IMAGE_SIZE
    }



}