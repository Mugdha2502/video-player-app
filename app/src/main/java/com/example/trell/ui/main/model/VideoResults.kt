package com.example.trell.ui.main.model


data class VideoResults(

	val videoFiles: List<Video>

)

data class Video(
	val title: String? = "",

	val thumbnail: String? = null,

	val filePath: String? = null,

	val isSelected: Boolean = false
)