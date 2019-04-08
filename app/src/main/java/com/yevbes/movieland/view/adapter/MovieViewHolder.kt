package com.yevbes.movieland.view.adapter

import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yevbes.movieland.App
import com.yevbes.movieland.model.res.MoviesRes

class MovieViewHolder (private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MoviesRes.Result) {
        binding.movie = movie

    }


}