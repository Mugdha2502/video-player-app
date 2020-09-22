package com.example.trell.ui.main.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trell.R
import com.example.trell.ui.main.model.Video
import com.example.trell.ui.main.viewmodel.MainActivityViewModel

class ContentListAdapter(private val response: List<Video>, private val bookMarkInterface: MainActivityViewModel.IBookMarkVideo): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_card_view, parent, false)
        return ContentItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return response.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ContentItemViewHolder).bind(
            response.get(position).title,
            response.get(position).filePath,
            response.get(position).isSelected,
            bookMarkInterface
        )
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        (holder as ContentItemViewHolder).initializePlayer()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        (holder as ContentItemViewHolder).releasePlayer()
    }

}
