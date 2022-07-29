package com.guilong.articledemo.ui.article

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.guilong.articledemo.BaseActivity
import com.guilong.articledemo.R
import com.guilong.articledemo.databinding.ActivityArticleDesBinding
import com.guilong.articledemo.logic.Article

class ArticleDesActivity : BaseActivity<ActivityArticleDesBinding>() {

    private lateinit var mArticle: Article//当前文章数据

    //添加跳转启动器
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                //获取修改数据结果
                val updResult =
                    result.data?.getBooleanExtra(OperaArticleActivity.KEY_ADD_RESULT, false)
                Toast.makeText(this, "数据已修改", Toast.LENGTH_SHORT).show()
            }
        }

    companion object {
        private const val KEY_ARTICLE = "key_article"

        /**
         * 启动当前页面
         *
         * @param article 文章数据（不能为空）
         */
        fun startAction(
            activity: Activity,
            article: Article
        ) {
            val intent = Intent(activity, ArticleDesActivity::class.java)
            intent.putExtra(KEY_ARTICLE, article)
            activity.startActivity(intent)
        }
    }

    override fun bindRootBinding(inflater: LayoutInflater) =
        ActivityArticleDesBinding.inflate(inflater)

    override fun initView() {
        super.initView()
        /*设置标题栏*/
        setSupportActionBar(binding.viewArticleDesToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.mipmap.bar_icon_back)
        }
    }

    override fun initData() {
        super.initData()
        /*获取跳转携带过来的文章数据*/
        mArticle = intent.getParcelableExtra(KEY_ARTICLE)!!
        //设置数据到控件上
        binding.tvArticleDesTitle.text = mArticle.title
        binding.tvArticleDesContent.text = mArticle.content
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //加载菜单
        menuInflater.inflate(R.menu.menu_des_fun, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_update -> {
                OperaArticleActivity.startAction(this, launcher, false, mArticle)
            }
            R.id.action_delete -> {

            }
        }

        return true
    }
}