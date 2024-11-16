package com.tytbutler.pantry

import android.app.Application

class PantryApplication : Application() {
    lateinit var container: AppContainer;

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}