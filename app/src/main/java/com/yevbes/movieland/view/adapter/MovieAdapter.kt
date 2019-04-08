package com.yevbes.movieland.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.adprogressbarlib.AdCircleProgress
import com.bumptech.glide.Glide
import com.yevbes.movieland.App
import com.yevbes.movieland.R
import com.yevbes.movieland.model.res.MoviesRes
import com.yevbes.movieland.utils.AppConfig

class MovieAdapter: RecyclerView.Adapter<MovieViewHolder>() {
    private var movies: List<MoviesRes.Result> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val rowBinding = MovieItemBinding.inflate(layoutInflater,p0,false)
        return MovieViewHolder(rowBinding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(p0: MovieViewHolder, p1: Int) {
        val currentMovie = movies[p1]
        p0.bind(currentMovie)
    }

    fun setMovies(movies: List<MoviesRes.Result>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}