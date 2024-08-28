package com.example.final_finalapp.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
//    lateinit var database: AppDb
//        private set
    override fun onCreate() {
        super.onCreate()
//        database = createDb(this)
    }
}