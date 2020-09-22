package com.example.trell.utils

import android.content.ContentResolver
import android.content.Context
import com.example.trell.App
import com.example.trell.data.AppDatabase
import com.example.trell.ui.main.view.MainActivity
import com.example.trell.ui.main.viewmodel.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun provideContext(): Context

    fun provideAppDatabase(): AppDatabase

    fun provideContentResolver(): ContentResolver

    fun inject(app: App)

    fun inject(activity: MainActivity)

    fun inject(viewModel: MainActivityViewModel)

}