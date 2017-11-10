package com.ooyala.sample.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.ooyala.sample.R
import com.ooyala.sample.utils.VideoData
import com.ooyala.sample.utils.VideoItemType
import kotlinx.android.synthetic.main.view_holder_item.view.*
import java.util.*

class VideoRecyclerAdapter(private val dataList: List<VideoData>, private val listener: (VideoData) -> Unit) : RecyclerView.Adapter<VideoRecyclerAdapter.ItemViewHolder>() {
  companion object {
    private var selectedPosition = 0
    private var isChosen = false
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bindItem(dataList[position], listener, position)

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder =
          ItemViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_item, parent, false))

  override fun getItemCount(): Int = dataList.size

  fun selectNext() {
    if (selectedPosition < dataList.size - 1) {
      selectedPosition++
      notifyItemChanged(selectedPosition)

      val previousPosition = selectedPosition - 1
      notifyItemChanged(previousPosition)
    }
  }

  fun selectPrevious() {
    if (selectedPosition > 1) {
      selectedPosition--
      notifyItemChanged(selectedPosition)

      val previousPosition = selectedPosition + 1
      notifyItemChanged(previousPosition)
    }
  }

  fun chooseCurrent() {
    isChosen = true
    notifyDataSetChanged()
  }

  class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindItem(data: VideoData, listener: (VideoData) -> Unit, position: Int) {
      itemView.videoTitleTextView.visibility = GONE
      if (data.type == VideoItemType.VIDEO) {
        itemView.sectionTitleTextView.visibility = GONE
        itemView.videoTitleTextView.visibility = VISIBLE
        itemView.videoTitleTextView.text = data.title
      } else {
        itemView.videoTitleTextView.visibility = GONE
        itemView.sectionTitleTextView.visibility = VISIBLE
        itemView.sectionTitleTextView.text = data.title
      }

      itemView.setOnClickListener { listener(data) }

      keyPressProcessing(data, position)
    }

    private fun keyPressProcessing(data: VideoData, position: Int) {
      if (position == selectedPosition && data.type != VideoItemType.SECTION) {
        if (isChosen) {
          itemView.videoTitleTextView.performClick()
          return
        }
        itemView.videoTitleTextView.setTextColor(Color.RED)
      } else {
        itemView.videoTitleTextView.setTextColor(Color.WHITE)
      }
    }
  }
}