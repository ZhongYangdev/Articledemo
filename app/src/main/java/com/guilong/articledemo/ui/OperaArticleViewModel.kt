package com.guilong.articledemo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilong.articledemo.logic.AppRepo
import com.guilong.articledemo.logic.Article
import com.guilong.articledemo.logic.ArticleRepository
import kotlinx.coroutines.launch

/**
 * Created by XGuiLong on 2022/7/28 10:09.
 *
 * 描述：主页UI数据交互层
 */
class OperaArticleViewModel : ViewModel() {

    //添加数据到本地的文章ID
    val articleIdLiveData = MutableLiveData<Article>()

    //修改文章状态结果
    val updStatusLiveData = MutableLiveData<Boolean>()

    //修改文章结果
    val updArticleLiveData = MutableLiveData<Boolean>()

    //未提交文章数据
    val notSubmitLiveData = MutableLiveData<List<Article>>()

    //文章数据
    private val articlesLiveData = MutableLiveData<List<Article>>()

    //提交文章结果
    val submitResultLiveData = Transformations.switchMap(articlesLiveData) { article ->
        ArticleRepository.addArticle(article)
    }

    /**
     * 添加文章
     */
    fun addArticle(article: Article) {
        viewModelScope.launch {
            val newArticle = AppRepo.addArticle(article)
            articleIdLiveData.postValue(newArticle)
        }
    }

    /**
     * 提交文章数据到服务器
     */
    fun submitArticle(articles: List<Article>) {
        articlesLiveData.value = articles
    }

    /**
     * 改变文章状态
     */
    fun updArticleStatus(articleId: Long) {
        viewModelScope.launch {
            AppRepo.updateArticleState(articleId)
        }
        updStatusLiveData.value = true
    }

    /**
     * 修改文章数据
     */
    fun updArticleData(article: Article) {
        viewModelScope.launch {
            AppRepo.updateArticle(article)
        }
        updArticleLiveData.value = true
    }

    /**
     * 加载所有未提交数据
     */
    fun loadAllNotSubmit() {
        viewModelScope.launch {
            val notSubmitArticles = AppRepo.loadNotSubmitArticle()
            notSubmitLiveData.postValue(notSubmitArticles)
        }
    }
}