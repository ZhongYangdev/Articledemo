package com.guilong.articledemo.ui

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.guilong.articledemo.BaseActivity
import com.guilong.articledemo.R
import com.guilong.articledemo.databinding.ActivityArticleListBinding
import com.guilong.articledemo.logic.Article

class ArticleListActivity : BaseActivity<ActivityArticleListBinding>() {

    //文章适配器
    private lateinit var mArticleAdapter: ArticleAdapter

    //viewModel
    private val viewModel by lazy {
        ViewModelProvider(this)[ArticleListViewModel::class.java]
    }

    //添加跳转启动器
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                //获取提交数据结果
                val submitResult =
                    result.data?.getBooleanExtra(OperaArticleActivity.KEY_ADD_RESULT, false)
                //获取所有文章数据
                if (submitResult!!) {
                    viewModel.loadAllArticle()
                }
            }
        }

    override fun bindRootBinding(inflater: LayoutInflater) =
        ActivityArticleListBinding.inflate(inflater)

    override fun initView() {
        super.initView()
        /*初始化标题栏*/
        setSupportActionBar(binding.viewArticleToolbar)
        binding.viewArticleToolbar.title = getString(R.string.text_article_list)
        /*初始化适配器相关*/
        mArticleAdapter = ArticleAdapter()
        binding.rvArticleList.apply {
            layoutManager = LinearLayoutManager(this@ArticleListActivity)
            adapter = mArticleAdapter
        }
    }

    override fun initData() {
        super.initData()
        /*获取所有文章数据*/
        viewModel.loadAllArticle()
    }

    override fun initEvent() {
        super.initEvent()
        /*适配器条目点击监听事件*/
        mArticleAdapter.setOnArticleItemClickListener(object :
            ArticleAdapter.OnArticleItemClickListener {
            override fun itemClick(article: Article) {
                ArticleDesActivity.startAction(this@ArticleListActivity, article)
            }
        })
    }

    override fun initObserver() {
        super.initObserver()
        /*观察文章数据变化*/
        viewModel.articleLiveData.observe(this) {
            if (it != null) {
                if (it.isEmpty()) {
                    updContainerUI(false)
                } else {
                    mArticleAdapter.setItemData(it)
                    updContainerUI(true)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //加载菜单
        menuInflater.inflate(R.menu.menu_list_fun, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                OperaArticleActivity.startAction(this, launcher, true, null)
            }
        }

        return true
    }

    /**
     * 更新内容UI
     */
    private fun updContainerUI(hasData: Boolean) {
        binding.tvArticleListEmpty.visibility = if (hasData) {
            View.GONE
        } else {
            View.VISIBLE
        }
        binding.rvArticleList.visibility = if (hasData) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}