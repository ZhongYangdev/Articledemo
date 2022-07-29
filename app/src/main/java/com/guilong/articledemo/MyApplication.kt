package com.guilong.articledemo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper

class MyApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var handler: Handler
    }

    override fun onCreate() {
        super.onCreate()
        /*获取全局上下文*/
        context = applicationContext
        /*获取Handler*/
        handler = Handler(Looper.getMainLooper())
    }
}