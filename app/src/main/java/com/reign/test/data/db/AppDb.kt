package com.reign.test.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.reign.test.data.model.StoryModel

@Database(entities = [StoryModel::class], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun storyDao(): StoryDao

    companion object {
        private var instance: AppDb? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDb {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDb::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }
    }
}