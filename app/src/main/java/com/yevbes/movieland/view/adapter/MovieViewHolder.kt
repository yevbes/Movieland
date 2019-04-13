package com.yevbes.movieland.view.adapter

import android.support.v7.widget.RecyclerView
import com.yevbes.movieland.databinding.MovieItemBinding
import com.yevbes.movieland.service.Movie

class MovieViewHolder (private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.movie = movie
        binding.executePendingBindings()
    }
}