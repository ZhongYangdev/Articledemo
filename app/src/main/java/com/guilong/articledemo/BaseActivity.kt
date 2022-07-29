package com.guilong.articledemo

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    open lateinit var binding: T

    companion object {
        const val TAG = "Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*载布局*/
        binding = bindRootBinding(layoutInflater)
        setContentView(binding.root)
        /*初始化控件*/
        initView()
        /*初始化数据*/
        initData()
        /*初始化事件*/
        initEvent()
        /*初始化观察者*/
        initObserver()
    }

    open fun initView() {}

    open fun initData() {}

    open fun initEvent() {}

    open fun initObserver() {}

    abstract fun bindRootBinding(inflater: LayoutInflater): T
}