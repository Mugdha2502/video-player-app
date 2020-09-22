package com.example.trell.utils;

import android.content.ContentResolver;
import android.content.Context;

import androidx.room.Room;

import com.example.trell.data.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;
    private static final String APP_DATABASE_NAME = "app-database";

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return Room.databaseBuilder(context, AppDatabase.class, APP_DATABASE_NAME)
                .fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    ContentResolver provideContentResolver() {
       return context.getContentResolver();
    }
}