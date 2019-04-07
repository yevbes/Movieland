package com.yevbes.movieland.repository.error

data class MovielandException(
    val code: Int, override val message: String
) : Throwable()


