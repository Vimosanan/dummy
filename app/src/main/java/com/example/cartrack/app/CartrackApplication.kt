package com.example.cartrack.app

import android.app.Application
import com.example.cartrack.di.AppComponent
import com.example.cartrack.di.AppModule
import com.example.cartrack.di.DaggerAppComponent

class CartrackApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}