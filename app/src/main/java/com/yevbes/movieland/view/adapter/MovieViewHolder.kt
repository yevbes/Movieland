package com.yevbes.movieland.view.adapter

import android.support.v7.widget.RecyclerView
import com.yevbes.movieland.databinding.MovieItemBinding
import com.yevbes.movieland.service.remote.model.res.MoviesRes

class MovieViewHolder (private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MoviesRes.Result) {
        binding.movie = movie
        binding.executePendingBindings()
    }


}