package com.guilong.articledemo.logic

import com.guilong.articledemo.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AppRepo {

    //文章数据操作层
    private val articleDao = ArticleDatabase.getDatabase(MyApplication.context).buildArticleDao()

    /**
     * 添加文章数据
     */
    suspend fun addArticle(article: Article): Article {
        val resultId: Long
        withContext(Dispatchers.IO) {
            resultId = articleDao.insertArticle(article)
        }
        if (resultId > 0) {
            article.articleId = resultId
        }
        return article
    }

    /**
     * 加载所有文章数据
     */
    suspend fun loadArticle(): List<Article> {
        val resultList = ArrayList<Article>()
        withContext(Dispatchers.IO) {
            resultList.addAll(articleDao.loadArticles())
        }
        return resultList
    }

    /**
     * 根据文章ID修改状态
     */
    suspend fun updateArticleState(id: Long) {
        withContext(Dispatchers.IO) {
            articleDao.updArticleStateById(id)
        }
    }

    /**
     * 修改文章数据
     */
    suspend fun updateArticle(article: Article) {
        withContext(Dispatchers.IO) {
            articleDao.updateArticle(article)
        }
    }

    /**
     * 加载所有未提交数据
     */
    suspend fun loadNotSubmitArticle(): List<Article> {
        val resultList = ArrayList<Article>()
        withContext(Dispatchers.IO) {
            val notSubmitList = articleDao.loadNotSubmitData()
            resultList.addAll(notSubmitList)
        }
        return resultList
    }
}