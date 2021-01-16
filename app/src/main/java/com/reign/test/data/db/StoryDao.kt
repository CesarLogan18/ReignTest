package com.reign.test.data.db


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.reign.test.data.model.StoryModel

@Dao
interface StoryDao {

    @Query("SELECT * FROM story_table ORDER BY created_at_seconds DESC")
    fun getSavedStories(): List<StoryModel>

    @Insert()
    fun insert(story: StoryModel): Long

    @Delete()
    fun delete(story: StoryModel)

}