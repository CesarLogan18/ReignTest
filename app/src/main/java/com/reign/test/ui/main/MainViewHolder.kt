package com.reign.test.ui.main

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reign.test.R

class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var title: TextView = view.findViewById(R.id.title)
    var authorAndDate: TextView = view.findViewById(R.id.authorAndDate)

}