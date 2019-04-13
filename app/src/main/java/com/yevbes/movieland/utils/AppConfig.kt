package com.yevbes.movieland.utils

interface AppConfig {
    companion object {
        private const val API_VERSION = 3
        const val BASE_URL = "https://api.themoviedb.org/$API_VERSION/"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"
        const val IMAGE_SIZE = "w185"
        const val API_KEY = "7806a3dc6071a20e6cad5396d5e28a0c"
        const val MOVIES_ARRAY_DATA = "results"

        const val LANGUAGE_REQUEST_PARAM = "language"
        const val PAGE_REQUEST_PARAM = "page"
        const val LANGUAGE = "en"
    }
}