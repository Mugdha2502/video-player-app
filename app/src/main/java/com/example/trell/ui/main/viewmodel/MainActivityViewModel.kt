package com.example.trell.ui.main.viewmodel

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.trell.data.AppDatabase
import com.example.trell.data.Videos
import com.example.trell.ui.main.model.Video
import com.example.trell.ui.main.model.VideoResults
import kotlinx.coroutines.*
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(
    private val contentResolver: ContentResolver,
    private val db: AppDatabase
) : ViewModel() {

    private lateinit var response: MutableLiveData<VideoResults>

    interface IBookMarkVideo {
        fun onBookMarkClicked(title: String, filePath: String)
        fun onBookMarkRemoved(filePath: String)
    }

    fun getVideoFiles(): VideoResults {
        val uri: Uri
        val cursor: Cursor?
        val column_index_data: Int
        val column_index_folder_name: Int
        val thum: Int
        var absolutePathOfImage: String? = null
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.MediaColumns.DATA,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Thumbnails.DATA
        )
        val orderBy = MediaStore.Video.Media.DATE_TAKEN
        cursor = contentResolver
            .query(uri, projection, null, null, "$orderBy DESC")
        column_index_data = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)!!
        column_index_folder_name =
            cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA)
        val listItems = ArrayList<Video>()
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data)

            listItems.add(
                Video(
                    cursor.getString(column_index_folder_name),
                    cursor.getString(thum),
                    absolutePathOfImage
                )
            )

        }
        return VideoResults(listItems)
    }


    fun bookMarkVideo(title: String, filePath: String) {
        GlobalScope.launch {
            db.bookmarkDao().insert(Videos(title, filePath))
        }
    }

    fun removeBookMark(filePath: String) {
        GlobalScope.launch {
            val video = db.bookmarkDao().getVideo(filePath)
            if (video != null)
                db.bookmarkDao().delete(video)

        }
    }

}