package com.yevbes.movieland.view.adapter

import android.arch.paging.PagedListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yevbes.movieland.databinding.MovieItemBinding
import com.yevbes.movieland.service.remote.model.res.MoviesRes

class MovieAdapter: PagedListAdapter<MoviesRes.Result, MovieViewHolder>(MoviesRes.Result.DIFF_CALLBACK) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val rowBinding = MovieItemBinding.inflate(layoutInflater,p0,false)
        return MovieViewHolder(rowBinding)
    }

    override fun onBindViewHolder(p0: MovieViewHolder, p1: Int) {
        p0.bind(getItem(p1)!!)
    }
}