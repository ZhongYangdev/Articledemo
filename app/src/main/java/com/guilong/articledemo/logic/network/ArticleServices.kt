package com.guilong.articledemo.logic.network

import com.guilong.articledemo.logic.SubmitResponse
import retrofit2.Call
import retrofit2.http.GET

interface ArticleServices {

    @GET("discovery/categories")
    fun submitArticle(): Call<SubmitResponse>
}