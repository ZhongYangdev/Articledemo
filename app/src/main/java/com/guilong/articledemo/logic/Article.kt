package com.guilong.articledemo.logic

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by XGuiLong on 2022/7/27 16:11.
 *
 * 描述：文章数据实体
 *
 * title：文章标题
 * content：文章内容
 * isSubmit：是否提交到服务器
 * articleId：文章ID（主键）（自增）
 */
@Entity(tableName = "tb_article")
data class Article(
    val title: String?,
    val content: String?,
    val isSubmit: Boolean
) : Parcelable{
    @PrimaryKey(autoGenerate = true)
    var articleId: Long = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
        articleId = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeByte(if (isSubmit) 1 else 0)
        parcel.writeLong(articleId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}