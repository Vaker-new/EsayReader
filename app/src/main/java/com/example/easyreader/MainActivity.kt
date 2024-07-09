package com.example.easyreader

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import com.example.easyreader.dao.data.Article
import com.example.easyreader.ui.MainView
import com.example.easyreader.ui.theme.EasyReaderTheme
import com.example.easyreader.viewmodel.ReaderViewModel
import com.example.easyreader.viewmodel.ReaderViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: ReaderViewModel

    private var articleList = mutableStateListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = (this.application as EasyReaderApplication).database
        viewModel =
            ReaderViewModelFactory(
                database.readerDao(), database.wordDao()
            ).create(
                ReaderViewModel::class.java
            )
        enableEdgeToEdge()
        setContent {
            EasyReaderTheme {
                MainView(articleList = articleList, onItemClick = ::onItemClick)
            }
        }
        observeData()
        viewModel.getAllArticle()
    }

    /**
     * 观察数据变化
     */
    private fun observeData() {
        viewModel.articleLiveData.observe(this) {
            if (it.isNotEmpty()) {
                articleList.clear()
                articleList.addAll(it)
            } else {
                viewModel.getAllArticle()
            }
        }
    }

    /**
     * 列表item点击事件
     */
    private fun onItemClick(article: Article) {
        val intent = Intent(this, ArticleDetailActivity::class.java)
        intent.putExtra("article", article)
        startActivity(intent)
    }
}