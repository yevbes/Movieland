package com.yevbes.movieland.service.remote.api

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.yevbes.movieland.service.Movie
import com.yevbes.movieland.utils.AppConfig
import java.lang.reflect.Type


class MoviesDeserializer : JsonDeserializer<ArrayList<Movie>> {

    private val TAG = MoviesDeserializer::class.java.simpleName

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ArrayList<Movie>? {
        var movies: ArrayList<Movie>? = null
        try {
            val jsonObject = json!!.asJsonObject
            val moviesJsonArray = jsonObject.getAsJsonArray(AppConfig.MOVIES_ARRAY_DATA)
            movies = ArrayList(moviesJsonArray.size())
            for (i in 0 until moviesJsonArray.size()) {
                val dematerialized = context!!.deserialize<Movie>(moviesJsonArray.get(i), Movie::class.java)
                movies.add(dematerialized)
            }
        } catch (e: JsonParseException) {
            Log.e(TAG, String.format("Could not deserialize Movie element: %s", json.toString()))
        }
        return movies
    }
}