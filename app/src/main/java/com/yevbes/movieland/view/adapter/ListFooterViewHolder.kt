package com.yevbes.movieland.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.yevbes.movieland.R
import com.yevbes.movieland.service.remote.State
import kotlinx.android.synthetic.main.movie_item_footer.view.*

class ListFooterViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bind(status: State?) {
        itemView.progress_bar.visibility = if (status == State.LOADING) VISIBLE else View.INVISIBLE
        itemView.tv_error.visibility = if (status == State.ERROR) VISIBLE else View.INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item_footer, parent, false)
            view.tv_error.setOnClickListener { retry() }
            return ListFooterViewHolder(view)
        }
    }
}