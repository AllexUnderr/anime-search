package com.example.animesearch.helper

import android.app.Application
import com.example.animesearch.component.AppComponent
import com.example.animesearch.component.AppComponent.Companion.AppModule
import com.example.animesearch.component.DaggerAppComponent

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(applicationContext))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}