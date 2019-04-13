package com.yevbes.movieland.service

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BindingAdapter
import android.support.v7.util.DiffUtil
import android.widget.ImageView
import com.app.adprogressbarlib.AdCircleProgress
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import com.yevbes.movieland.utils.AppConfig

@Entity(tableName = "movie")
data class Movie(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName(value = "id")
    val id: Int,

    @ColumnInfo(name = "vote_count")
    @SerializedName(value = "vote_count")
    val voteCount: Int,

    @ColumnInfo(name = "video")
    @SerializedName(value = "video")
    val video: Boolean,

    @ColumnInfo(name = "vote_average")
    @SerializedName(value = "vote_average")
    val voteAverage: Float,

    @ColumnInfo(name = "title")
    @SerializedName(value = "title")
    val title: String,

    @ColumnInfo(name = "popularity")
    @SerializedName(value = "popularity")
    val popularity: Float,

    @ColumnInfo(name = "poster_path")
    @SerializedName(value = "poster_path")
    val posterPath: String,

    @ColumnInfo(name = "original_language")
    @SerializedName(value = "original_language")
    val originalLanguage: String,

    @ColumnInfo(name = "original_title")
    @SerializedName(value = "original_title")
    val originalTitle: String,

    @ColumnInfo(name = "backdrop_path")
    @SerializedName(value = "backdrop_path")
    val backdropPath: String,

    @ColumnInfo(name = "adult")
    @SerializedName(value = "adult")
    val adult: Boolean,

    @ColumnInfo(name = "overview")
    @SerializedName(value = "overview")
    val overview: String,

    @ColumnInfo(name = "release_date")
    @SerializedName(value = "release_date")
    val releaseDate: String
) {
    companion object {
        @JvmStatic
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }

        @JvmStatic
        @BindingAdapter("app:imageUrl")
        fun setImageUrl(view: ImageView, url: String) {
            Glide.with(view.context)
                .load(getImageUrl() +url)
                //            .placeholder(lottieAnimationView.drawable)
                .into(view)
        }

        @JvmStatic
        @BindingAdapter("app:adpgb_progress")
        fun setProgress(view: AdCircleProgress, voteAverage: Double) {
            view.progress = (voteAverage * 10).toFloat()
        }

        private fun getImageUrl(): String {
            return AppConfig.BASE_IMAGE_URL + AppConfig.IMAGE_SIZE
        }
    }
}