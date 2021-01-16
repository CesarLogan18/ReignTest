package com.reign.test.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.reign.test.R
import com.reign.test.Utils
import com.reign.test.data.model.StoryModel

class MainAdapter(
    private val storyList: MutableList<StoryModel>,
    private val cellClickListener: CellClickListener,
    private val context: Context
) :
    RecyclerView.Adapter<MainViewHolder>() {


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.story_item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val story = storyList[position]

        holder.title.text =
            if (story.story_title != null) {
                if (story.story_title != "Null") story.story_title else story.title ?: "No Title"
            } else "No Title"


        holder.authorAndDate.text =
            context.getString(
                R.string.authorAndDateTemplate,
                story.author,
                getCreatedAtStringValue(story.created_at_i)
            )

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(storyList[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int {
        return storyList.size
    }

    private fun getCreatedAtStringValue(created_at_long: Long): String {
        val diff = Utils.getCurrentTimeDiffInSeconds(created_at_long)

        val days = diff / (60 * 60 * 24);
        val hours = (diff - (60 * 60 * 24 * days)) / (60 * 60)
        val min =
            (diff - (60 * 60 * 24 * days) - (60 * 60 * hours)) / (60)

        return when {
            days > 0 -> "${days}d"
            hours > 0 -> "${hours}h"
            min > 0 -> "${min}m"
            else -> "seconds ago"
        }
    }


}