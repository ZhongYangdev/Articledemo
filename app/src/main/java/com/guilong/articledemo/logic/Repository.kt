package com.guilong.articledemo.logic

import androidx.lifecycle.liveData
import com.guilong.articledemo.logic.network.Network
import kotlinx.coroutines.Dispatchers

object Repository {

    /**
     * 添加文章
     */
    fun addArticle(article: Article) = liveData(Dispatchers.IO) {
        val result = try {
            //请求网络数据
            val defaultData = Network.getDefaultData()

            //处理请求结果
            if (defaultData.success) {
                val articleId = article.articleId
                Result.success(articleId)
            } else {
                Result.failure(RuntimeException("请求失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

        emit(result)
    }
}