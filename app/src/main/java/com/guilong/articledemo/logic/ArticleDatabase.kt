package com.guilong.articledemo.logic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by XGuiLong on 2022/7/27 16:36.
 *
 * 描述：文章数据管理类
 */
@Database(version = 1, entities = [Article::class])
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun buildArticleDao(): ArticleDao

    companion object {
        private var sInstance: ArticleDatabase? = null

        fun getDatabase(context: Context): ArticleDatabase {
            sInstance?.let { return it }
            return Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    "article_database"
            ).build().apply {
                sInstance = this
            }
        }
    }
}