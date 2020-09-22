package com.example.trell

import android.app.Application
import android.content.Context
import com.example.trell.utils.AppComponent
import com.example.trell.utils.AppModule
import com.example.trell.utils.DaggerAppComponent

class App(context: Context) : Application() {

    val component: AppComponent =
        DaggerAppComponent
                .builder()
                .appModule(AppModule(context))
                .build()

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}