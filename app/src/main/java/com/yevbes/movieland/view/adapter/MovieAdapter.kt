package com.yevbes.movieland.view.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yevbes.movieland.databinding.MovieItemBinding
import com.yevbes.movieland.service.Movie
import com.yevbes.movieland.service.remote.State

class MovieAdapter(private val retry: () -> Unit): PagedListAdapter<Movie, RecyclerView.ViewHolder>(Movie.DIFF_CALLBACK) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2
    private var state = State.LOADING

    /**
     * On create view holder
     */
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return when(p1) {
            DATA_VIEW_TYPE -> {
                val layoutInflater = LayoutInflater.from(p0.context)
                val rowBinding = MovieItemBinding.inflate(layoutInflater,p0,false)
                MovieViewHolder(rowBinding)
            }
            else -> {
                ListFooterViewHolder.create(retry, p0)
            }
        }
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (getItemViewType(p1) == DATA_VIEW_TYPE)
            (p0 as MovieViewHolder).bind(getItem(p1)!!)
        else (p0 as ListFooterViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}