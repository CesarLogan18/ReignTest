package com.reign.test.data.repository

import android.app.Application
import com.reign.test.data.db.AppDb
import com.reign.test.data.db.StoryDao
import com.reign.test.data.model.StoryModel
import com.reign.test.data.network.NetworkManager
import com.reign.test.data.network.StoryResponse
import com.reign.test.data.preferences.PreferencesHelper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository(application: Application, val listener: StoryRepositoryListener) {

    private var allStories: MutableList<StoryModel> = arrayListOf()

    private var storyDao: StoryDao
    private val database = AppDb.getInstance(application)

    private var lastUpdate: Long = 0
    private val preferencesHelper = PreferencesHelper(application)

    init {
        storyDao = database.storyDao()
        subscribeOnBackground {
            allStories.addAll(storyDao.getSavedStories())
            lastUpdate = preferencesHelper.getLastUpdate()
        }
    }

    fun getAllStories(): MutableList<StoryModel> {
        return allStories
    }

    fun removeStory(position: Int) {
        val item = allStories[position]
        allStories.removeAt(position)
        removeStoryInDB(item)
    }

    fun updateStories() {
        val call = NetworkManager.getStories()
        call.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(
                call: Call<StoryResponse>?,
                response: Response<StoryResponse>?
            ) {
                if (response?.code() == 200) {
                    val apiResponse = response.body()!!
                    val list = apiResponse.hits
                    if (list != null && list.isNotEmpty())
                        updateRepo(list)
                    listener.onStoriesUpdated()

                }
            }

            override fun onFailure(call: Call<StoryResponse>?, t: Throwable?) {
                listener.onStoriesUpdated()
                listener.onNetworkFailed()
            }
        })
    }

    private fun insertStoryInDB(item: StoryModel) {
        subscribeOnBackground {
            val id = storyDao.insert(item)
            item.id = id
        }
    }

    private fun removeStoryInDB(item: StoryModel) {
        subscribeOnBackground {
            storyDao.delete(item)
        }
    }

    private fun updateRepo(resultList: ArrayList<StoryModel>) {
        resultList.reverse()
        for (item in resultList) {
            if (item.created_at_i > lastUpdate) {
                lastUpdate = item.created_at_i
                preferencesHelper.setLastUpdateTime(lastUpdate)

                allStories.add(0, item)
                insertStoryInDB(item)
            }
        }
    }

    private fun subscribeOnBackground(function: () -> Unit) {
        Single.fromCallable {
            function()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

}


