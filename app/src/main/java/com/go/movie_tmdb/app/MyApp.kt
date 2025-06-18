package com.go.movie_tmdb.app

import android.app.Application
import com.go.movie_tmdb.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // start Koin
        startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
}