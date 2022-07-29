package com.guilong.articledemo.ui.article

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModelProvider
import com.guilong.articledemo.BaseActivity
import com.guilong.articledemo.R
import com.guilong.articledemo.databinding.ActivityOperaArticleBinding
import com.guilong.articledemo.logic.Article
import com.guilong.articledemo.ui.LoadingDialog

class OperaArticleActivity : BaseActivity<ActivityOperaArticleBinding>() {

    private var mIsAdd: Boolean = false//当前是否为添加文章
    private var mArticle: Article? = null//当前文章数据，只有修改时才有数据
    private lateinit var mLoadingDialog: LoadingDialog//加载中对话框
    private val mSubmitList = ArrayList<Article>()//当前待提交数据集合
    private var mSubmitSize = 0//当前已提交数据大小

    private var mCurrentArticleId: Long = 0//当前文章ID

    //主页ViewModel
    private val viewModel by lazy {
        ViewModelProvider(this)[OperaArticleViewModel::class.java]
    }

    companion object {
        private const val KEY_IS_ADD = "key_is_add"
        private const val KEY_ARTICLE = "key_article"
        const val KEY_ADD_RESULT = "key_add_result"

        /**
         * 启动当前页面
         *
         * @param isAdd 是否为添加
         */
        fun startAction(
            activity: Activity,
            launcher: ActivityResultLauncher<Intent>,
            isAdd: Boolean = false,
            article: Article?
        ) {
            val intent = Intent(activity, OperaArticleActivity::class.java)
            intent.putExtra(KEY_IS_ADD, isAdd)
            intent.putExtra(KEY_ARTICLE, article)
            launcher.launch(intent)
        }
    }

    override fun bindRootBinding(inflater: LayoutInflater) =
        ActivityOperaArticleBinding.inflate(inflater)

    override fun initView() {
        super.initView()
        /*初始化标题栏*/
        setSupportActionBar(binding.viewOperaArticleToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.mipmap.bar_icon_back)
        }
        /*初始化加载中对话框*/
        mLoadingDialog = LoadingDialog(this)
    }

    override fun initData() {
        super.initData()
        /*判断当前是否添加*/
        mIsAdd = intent.getBooleanExtra(KEY_IS_ADD, false)
        //设置标题
        binding.viewOperaArticleToolbar.title = if (mIsAdd) {
            "添加文章"
        } else {
            "修改文章"
        }
        //设置已有数据
        if (!mIsAdd) {
            mArticle = intent.getParcelableExtra(KEY_ARTICLE)
            if (mArticle != null) {
                //标题
                val title = mArticle!!.title
                if (title != null) {
                    binding.etMainTitle.setText(title)
                }
                //内容
                val content = mArticle!!.content
                if (title != null) {
                    binding.etMainContent.setText(content)
                }
            }
        }
    }

    override fun initEvent() {
        super.initEvent()
        /*提交按钮点击事件*/
        binding.btnMainSubmit.setOnClickListener {
            //获取输入数据
            val title = binding.etMainTitle.text.toString()
            val content = binding.etMainContent.text.toString()
            //封装文章数据实体
            val article = Article(title, content, false)
            //判断当前是添加还是修改
            if (mIsAdd) {
                //添加到本地数据库
                viewModel.addArticle(article)
            } else {
                //修改数据
                viewModel.updArticleData(article)
            }
            //显示加载中对话框
            mLoadingDialog.message = "正在提交数据"
            mLoadingDialog.show()
        }
    }

    override fun initObserver() {
        super.initObserver()
        /*文章数据变化监听*/
        viewModel.articleIdLiveData.observe(this) {
            if (it != null) {
                Log.i(TAG, "文章 ${it.articleId} 已添加至本地数据库 ==>")
                //获取其他未提交数据
                viewModel.loadAllNotSubmit()
            }
        }
        /*提交结果变化监听*/
        viewModel.submitResultLiveData.observe(this) { result ->
            val submitArticleId = result.getOrNull()
            if (submitArticleId != null) {
                //改变当前数据状态
                viewModel.updArticleStatus(submitArticleId)
                //当前文章ID数据赋值
                mCurrentArticleId = submitArticleId
            } else {
                Toast.makeText(this, "提交失败", Toast.LENGTH_SHORT).show()
                backPreLv()
            }
        }
        /*修改文章状态结果变化监听*/
        viewModel.updStatusLiveData.observe(this) {
            if (it != null) {
                mSubmitSize++
                Log.i(TAG, "已提交数据大小 ==> $mSubmitSize 当前数据大小 ==> ${mSubmitList.size}")
                //等待全部提交完成 返回上一级
                if (mSubmitSize == mSubmitList.size) {
                    mLoadingDialog.dismiss()
                    backPreLv()
                }
            }
        }
        /*修改文章数据结果变化监听*/
        viewModel.updArticleLiveData.observe(this) {
            if (it != null) {
                backPreLv()
            }
        }
        /*未提交数据变化监听*/
        viewModel.notSubmitLiveData.observe(this) {
            if (it != null) {
                //添加到待提交数据集合
                mSubmitList.addAll(it)
                //提交文章数据
                for (article in mSubmitList) {
                    viewModel.submitArticle(article)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return true
    }

    /**
     * 返回上一级
     */
    private fun backPreLv() {
        val intent = Intent()
        intent.putExtra(KEY_ADD_RESULT, true)
        setResult(RESULT_OK, intent)
        finish()
    }
}