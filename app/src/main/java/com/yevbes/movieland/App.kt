package com.yevbes.movieland

import android.app.Application
import timber.log.Timber



/**
 * Best practice
 */
class App : Application() {

    companion object {
        private lateinit var sApplication: Application

        /**
         * Static method which return Application
         */
        @JvmStatic
        fun getApplication(): Application {
            return sApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this

        // Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}