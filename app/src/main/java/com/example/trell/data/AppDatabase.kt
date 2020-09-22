package com.example.trell.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Videos::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}
