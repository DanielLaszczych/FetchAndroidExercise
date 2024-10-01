package com.example.fetchexercise

import android.app.Application
import com.example.fetchexercise.data.AppContainer
import com.example.fetchexercise.data.DefaultAppContainer

class FetchApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}