package com.reign.test.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.reign.test.data.model.StoryModel
import com.reign.test.data.repository.StoryRepositoryListener
import com.reign.test.data.repository.StoryRepository

class MainViewModel(app: Application) : AndroidViewModel(app), StoryRepositoryListener {
    var listener: StoryRepositoryListener? = null
    private val repository = StoryRepository(app, this)

    fun updateStories() {
        repository.updateStories()
    }

    fun getAllStories(): MutableList<StoryModel> {
        return repository.getAllStories()
    }

    fun removeStory(position: Int){
        repository.removeStory(position)
    }

    override fun onStoriesUpdated() {
        listener?.onStoriesUpdated()
    }

    override fun onNetworkFailed() {
        listener?.onNetworkFailed()
    }


}