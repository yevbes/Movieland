package com.yevbes.movieland.service

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.yevbes.movieland.service.model.dao.MovieDao
import com.yevbes.movieland.service.model.entities.Movie
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao


    companion object {
        private lateinit var instance: MovieDatabase
        @Synchronized
        fun getInstance(context: Context): MovieDatabase {
            if (!::instance.isInitialized) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java, "movie_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Completable.fromAction {
                    instance.movieDao().insert(
                        Movie(
                            title = "Hola",
                            description = "lalalalala",
                            urlImage = "htpp.png",
                            rating = 10f,
                            date = Date(),
                            author = "Yev Bes"
                        )
                    )
                }.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(object : CompletableObserver {
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onError(e: Throwable) {

                        }

                    })
            }
        }
    }

}