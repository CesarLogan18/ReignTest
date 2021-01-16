package com.reign.test.data.repository

interface StoryRepositoryListener {
    fun onStoriesUpdated()
    fun onNetworkFailed()
}