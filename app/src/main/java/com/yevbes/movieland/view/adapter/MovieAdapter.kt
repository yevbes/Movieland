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

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieHolder>() {
    private var movies: List<MoviesRes.Result> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.movie_item, p0, false)
        return MovieHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(p0: MovieHolder, p1: Int) {
        val currentMovie = movies[p1]
        p0.textViewTitle.text = currentMovie.title
        p0.textViewOverview.text = currentMovie.overview
        p0.textViewReleaseDate.text = currentMovie.releaseDate
        p0.addCircleProgressRating.progress = (currentMovie.voteAverage * 10).toFloat()
        val imgUrl = AppConfig.BASE_IMAGE_URL + AppConfig.IMAGE_SIZE + currentMovie.posterPath
        Glide.with(App.getApplication())
            .load(imgUrl)
//            .placeholder(lottieAnimationView.drawable)
            .into(p0.imageViewPoster)
    }

    fun setMovies(movies: List<MoviesRes.Result>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textViewTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var textViewOverview: TextView = itemView.findViewById(R.id.tvOverview)
        var textViewReleaseDate: TextView = itemView.findViewById(R.id.tvReleaseDate)
        var addCircleProgressRating: AdCircleProgress = itemView.findViewById(R.id.circularRating)
        var imageViewPoster: ImageView = itemView.findViewById(R.id.poster)
    }
}