package com.yevbes.movieland.service.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.yevbes.movieland.service.model.entities.Movie

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAllMovies()

    @Query("SELECT * FROM movie_table ORDER BY date")
    fun getAllMovies() : LiveData<List<Movie>>
}