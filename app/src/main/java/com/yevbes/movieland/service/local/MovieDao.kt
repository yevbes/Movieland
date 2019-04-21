package com.yevbes.movieland.service.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.yevbes.movieland.service.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movie")
    fun deleteAllMovies()

    @Query("SELECT * FROM movie ORDER BY title")
    fun getAllMovies() : LiveData<List<Movie>>
}