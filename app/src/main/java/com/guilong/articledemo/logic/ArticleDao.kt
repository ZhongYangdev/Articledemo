package com.guilong.articledemo.logic

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Created by XGuiLong on 2022/7/27 16:15.
 *
 * 描述：数据操作dao层
 */
@Dao
interface ArticleDao {

    /**
     * 添加文章
     *
     * @param article 文章数据实体
     */
    @Insert
    fun insertArticle(article: Article): Long

    /**
     * 修改文章
     *
     * @param article 文章数据实体
     */
    @Update
    fun updateArticle(article: Article)

    /**
     * 根据数据ID修改 是否提交到服务器 状态
     */
    @Query("update tb_article set isSubmit = '1' where articleId = :id")
    fun updArticleStateById(id: Long)

    /**
     * 查询所有文章
     */
    @Query("select * from tb_article")
    fun loadArticles(): List<Article>

    /**
     * 通过ID删除文章数据
     *
     * @param id 文章ID
     */
    @Query("delete from tb_article where articleId = :id")
    fun deleteArticleById(id: Long): Int

    /**
     * 加载所有未提交文章数据
     */
    @Query("select * from tb_article where isSubmit = '0'")
    fun loadNotSubmitData(): List<Article>
}