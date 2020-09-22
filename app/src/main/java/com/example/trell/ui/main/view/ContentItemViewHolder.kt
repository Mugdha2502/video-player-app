package com.example.trell.ui.main.view

import android.net.Uri
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trell.R
import com.example.trell.ui.main.viewmodel.MainActivityViewModel
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory


class ContentItemViewHolder(val root: View) : RecyclerView.ViewHolder(root), View.OnClickListener{
    private val textView: TextView = root.findViewById(R.id.nameAgeTxt)
    private val videoView: PlayerView = root.findViewById(R.id.videoView)
    private val bookMarkBtn: ImageButton = root.findViewById(R.id.save_bookmark_btn)

    var playWhenReady: Boolean = true
    var playbackPosition: Long = 0
    var currentWindow: Int = 0
    var filePath: String? = ""
    lateinit var bookMarkInterface: MainActivityViewModel.IBookMarkVideo
    var isSelected = false

    fun bind(name: String?, filePath: String?, isSelected: Boolean, bookMarkInterface: MainActivityViewModel.IBookMarkVideo) {
        textView.text = name
        this.filePath = filePath
        this.isSelected = isSelected
        this.bookMarkInterface = bookMarkInterface
        bookMarkBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (!isSelected) {
            isSelected = true
            bookMarkBtn.setImageResource(R.drawable.ic_bookmark_selected)
            bookMarkInterface.onBookMarkClicked(textView.text.toString(), filePath!!)
        } else {
            isSelected = false
            bookMarkBtn.setImageResource(R.drawable.ic_bookmark)
            bookMarkInterface.onBookMarkRemoved(filePath!!)
        }
    }

    fun initializePlayer() {
        videoView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        videoView.player = SimpleExoPlayer.Builder(root.context).build()
        val uri = Uri.parse(filePath)
        val mediaSource = buildMediaSource(uri)
        videoView.player?.playWhenReady = playWhenReady
        videoView.player?.seekTo(currentWindow, playbackPosition);
        (videoView.player as SimpleExoPlayer).prepare(mediaSource!!, false, false);
    }

    private fun buildMediaSource(uri: Uri): MediaSource? {
        val dataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(root.context, "trell-app")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)
    }

    fun releasePlayer() {
            playWhenReady = videoView.player?.playWhenReady!!
            playbackPosition = videoView.player?.currentPosition!!
            currentWindow = videoView.player?.currentWindowIndex!!
            videoView.player?.release()
    }
}