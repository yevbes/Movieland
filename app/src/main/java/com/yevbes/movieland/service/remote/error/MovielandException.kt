package com.yevbes.movieland.service.remote.error

data class MovielandException(
    val code: Int, override val message: String
) : Throwable()


