package com.guilong.articledemo.logic

import androidx.lifecycle.liveData
import com.guilong.articledemo.logic.network.ArticleNetwork
import kotlinx.coroutines.Dispatchers

object ArticleRepository {

    /**
     * 添加文章
     */
    fun addArticle(article: List<Article>) = liveData(Dispatchers.IO) {
        val result = try {
            //请求网络数据
            val submitResponse = ArticleNetwork.submitArticles()

            //处理请求结果
            if (submitResponse.success) {
                val successIds = ArrayList<Long>()
                for (article in article) {
                    successIds.add(article.articleId)
                }
                Result.success(successIds)
            } else {
                Result.failure(RuntimeException("请求失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

        emit(result)
    }
}