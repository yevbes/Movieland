package com.yevbes.movieland.service.model.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "movie_table")
data class Movie(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val title: String,
    val description: String,
    val author: String,
    val urlImage: String,
    val rating: Float,
    val date: Date
)