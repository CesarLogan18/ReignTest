package com.reign.test.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "story_table",
)
data class StoryModel(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "story_title") val story_title: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "created_at_seconds") val created_at_i: Long,
    @ColumnInfo(name = "url") val story_url: String?
)

