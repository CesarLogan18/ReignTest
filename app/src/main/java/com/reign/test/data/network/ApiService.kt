package com.reign.test.data.network

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/search_by_date?query=mobile")
    fun getStoryList(): Call<StoryResponse>
}