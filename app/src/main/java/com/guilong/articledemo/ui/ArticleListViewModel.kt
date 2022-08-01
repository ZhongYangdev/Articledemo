package com.guilong.articledemo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilong.articledemo.logic.AppRepo
import com.guilong.articledemo.logic.Article
import kotlinx.coroutines.launch

class ArticleListViewModel : ViewModel() {

    //文章数据
    val articleLiveData = MutableLiveData<List<Article>>()

    /**
     * 加载所有文章数据
     */
    fun loadAllArticle() {
        viewModelScope.launch {
            val loadArticle = AppRepo.loadArticle()
            articleLiveData.postValue(loadArticle)
        }
    }
}