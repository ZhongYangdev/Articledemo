package com.guilong.articledemo.ui.article

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guilong.articledemo.databinding.ItemArticleBinding
import com.guilong.articledemo.logic.Article

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    //当前数据集合
    private val mData = ArrayList<Article>()
    private var mOnArticleItemClickListener: OnArticleItemClickListener? = null

    inner class ViewHolder(binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTitle: TextView = binding.tvArticleIemTitle
        val tvContent: TextView = binding.tvArticleItemContent
        val tvStatus: TextView = binding.tvArticleItemStatus
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*绑数据*/
        val article = mData[position]
        //标题
        val title = article.title
        holder.tvTitle.text = if (TextUtils.isEmpty(title)) {
            "未知标题"
        } else {
            title
        }
        //内容
        val content = article.content
        holder.tvContent.text = if (TextUtils.isEmpty(content)) {
            "未知内容"
        } else {
            content
        }
        //状态
        holder.tvStatus.isEnabled = article.isSubmit
        holder.tvStatus.text = if (article.isSubmit) {
            "已提交"
        } else {
            "未提交"
        }
        /*条目点击事件*/
        holder.itemView.setOnClickListener {
            mOnArticleItemClickListener?.itemClick(article)
        }
    }

    override fun getItemCount() = mData.size

    /**
     * 设置条目数据
     */
    fun setItemData(articles: List<Article>) {
        mData.clear()
        mData.addAll(articles)

        notifyDataSetChanged()
    }

    fun setOnArticleItemClickListener(listener: OnArticleItemClickListener) {
        mOnArticleItemClickListener = listener
    }

    interface OnArticleItemClickListener {

        fun itemClick(article: Article)
    }
}