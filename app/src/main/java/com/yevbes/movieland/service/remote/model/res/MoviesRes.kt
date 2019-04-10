package com.yevbes.movieland.service.remote.model.res


import android.databinding.BindingAdapter
import android.support.v7.util.DiffUtil
import android.widget.ImageView
import com.app.adprogressbarlib.AdCircleProgress
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import com.yevbes.movieland.utils.AppConfig

data class MoviesRes(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
) {
    data class Result(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("backdrop_path")
        val backdropPath: String,
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        @SerializedName("id")
        val id: Int,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_title")
        val originalTitle: String,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("poster_path")
        val posterPath: String,
        @SerializedName("release_date")
        val releaseDate: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("video")
        val video: Boolean,
        @SerializedName("vote_average")
        val voteAverage: Double,
        @SerializedName("vote_count")
        val voteCount: Int
    ) {
        companion object {
            @JvmStatic
            var DIFF_CALLBACK: DiffUtil.ItemCallback<MoviesRes.Result> = object : DiffUtil.ItemCallback<MoviesRes.Result>() {
                override fun areItemsTheSame(oldItem: MoviesRes.Result, newItem: MoviesRes.Result): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: MoviesRes.Result, newItem: MoviesRes.Result): Boolean {
                    return oldItem.id == newItem.id
                }
            }

            @JvmStatic
            @BindingAdapter("app:imageUrl")
            fun setImageUrl(view: ImageView, url: String) {
                Glide.with(view.context)
                    .load(getImageUrl()+url)
      //            .placeholder(lottieAnimationView.drawable)
                    .into(view)
            }

            @JvmStatic
            @BindingAdapter("app:progress")
            fun setProgress(view: AdCircleProgress, voteAverage: Double) {
                view.progress = (voteAverage * 10).toFloat()
            }

            private fun getImageUrl(): String {
                // The URL will usually come from a model (i.e Profile)
                //return AppConfig.BASE_IMAGE_URL + AppConfig.IMAGE_SIZE + currentMovie.posterPath
                return AppConfig.BASE_IMAGE_URL + AppConfig.IMAGE_SIZE
            }
        }
    }
}