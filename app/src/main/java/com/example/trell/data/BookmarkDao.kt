package com.example.trell.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
abstract class BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(data: Videos)

    @Insert
    abstract fun insertAll(vararg data: Videos)

    @Delete
    abstract fun delete(data: Videos)

    @Query("SELECT * FROM video_data WHERE filePath IS :path")
    abstract fun getVideo(path: String):Videos?

    @Query("SELECT count(*) FROM video_data")
    abstract fun count(): Int

    @Query("DELETE FROM video_data")
    abstract fun clear()

}
