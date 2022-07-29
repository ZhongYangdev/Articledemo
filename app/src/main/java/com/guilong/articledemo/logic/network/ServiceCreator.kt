package com.guilong.articledemo.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    const val BASE_URL = "https://api.sunofbeaches.com/shop/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * 创建api接口动态对象
     */
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    /**
     * api接口获取方法
     */
    inline fun <reified T> create(): T = create(T::class.java)
}