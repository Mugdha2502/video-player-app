package com.example.trell.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_data")
class Videos(
	val title: String,
	@PrimaryKey
	val filePath: String
)
