package com.yevbes.movieland

import android.app.Application

class App : Application() {

    companion object {
        private lateinit var sApplication: Application

        @JvmStatic
        fun getApplication(): Application {
            return sApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
    }
}