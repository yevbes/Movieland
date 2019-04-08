package com.yevbes.movieland.model.error

data class MovielandException(
    val code: Int, override val message: String
) : Throwable()


