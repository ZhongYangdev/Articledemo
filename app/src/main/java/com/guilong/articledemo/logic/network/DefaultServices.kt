package com.guilong.articledemo.logic.network

import com.guilong.articledemo.logic.DataResponse
import retrofit2.Call
import retrofit2.http.GET

interface DefaultServices {

    @GET("discovery/categories")
    fun getData(): Call<DataResponse>
}