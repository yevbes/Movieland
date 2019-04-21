package com.yevbes.movieland.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yevbes.movieland.databinding.MovieItemBinding
import com.yevbes.movieland.service.Movie

class MovieAdapter(private var movies: ArrayList<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onBindViewHolder(p0: MovieViewHolder, p1: Int) {
        p0.bind(movies[p1])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    /**
     * On create view holder
     */
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val rowBinding = MovieItemBinding.inflate(layoutInflater, p0, false)
        return MovieViewHolder(rowBinding)
    }

    fun setMovieItems(movies: ArrayList<Movie>){
        this.movies = movies
        notifyDataSetChanged()
    }

}