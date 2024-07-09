package com.example.easyreader

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.easyreader.dao.data.Article
import com.example.easyreader.ui.ArticleDetail
import com.example.easyreader.ui.theme.EasyReaderTheme
import com.example.easyreader.ui.theme.Red
import com.example.easyreader.viewmodel.ReaderViewModel
import com.example.easyreader.viewmodel.ReaderViewModelFactory

class ArticleDetailActivity : ComponentActivity() {

    private lateinit var viewModel: ReaderViewModel

    private var article: Article? = null

    private val annotatedString = mutableStateOf(AnnotatedString(""))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentData()
        val database = (this.application as EasyReaderApplication).database
        viewModel =
            ReaderViewModelFactory(
                database.readerDao(), database.wordDao()
            ).create(
                ReaderViewModel::class.java
            )
        enableEdgeToEdge()
        annotatedString.value = AnnotatedString(article?.content ?: "")
        setContent {
            EasyReaderTheme {
                ArticleDetail(
                    article?.title ?: "",
                    annotatedString.value,
                    onBackClick = ::onBackClick,
                    onWordClick = ::onWordClick
                )
            }
        }
        observeData()
        viewModel.getAllWord()
    }

    /**
     * 获取传递过来的文章数据
     */
    private fun getIntentData() {
        article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getSerializableExtra("article", Article::class.java)
        } else {
            intent?.getSerializableExtra("article") as Article
        }
    }

    /**
     * 观察数据变化
     */
    private fun observeData() {
        viewModel.wordLiveData.observe(this) {
            Log.d("TAG", "observeData: ${it.size}")
        }
    }

    /**
     * 返回上一级页面
     */
    private fun onBackClick() {
        finish()
    }

    /**
     * 点击单词时触发
     */
    private fun onWordClick(offset: Int) {
        findWordAtIndex(article?.content ?: "", offset)
    }

    /**
     * 根据索引找到单词，并高亮显示
     */
    private fun findWordAtIndex(sentence: String, index: Int) {
        if (index < 0 || index >= sentence.length || sentence[index] == ' ' || sentence[index] == '\n') {
            return
        }

        // 确定单词的结束位置
        var endIndex = index
        while (endIndex < sentence.length && sentence[endIndex] != ' ') {
            endIndex++
        }

        // 确定单词的开始位置
        var startIndex = index
        while (startIndex > 0 && sentence[startIndex - 1] != ' ') {
            startIndex--
        }

        //给点击的单词设置高亮
        val annotatedText = buildAnnotatedString {
            append(sentence.substring(0, startIndex))
            withStyle(
                style = SpanStyle(
                    color = Red,
                )
            ) {
                append(sentence.substring(startIndex, endIndex))
            }
            append(sentence.substring(endIndex, sentence.length))
        }
        annotatedString.value = annotatedText
    }
}