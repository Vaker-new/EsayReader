package com.example.easyreader.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyreader.dao.ReaderDao
import com.example.easyreader.dao.WordDao
import com.example.easyreader.dao.data.Article
import com.example.easyreader.dao.data.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReaderViewModel(private val readerDao: ReaderDao, private val wordDao: WordDao) :
    ViewModel() {

    val articleLiveData: MutableLiveData<List<Article>> = MutableLiveData()
    val wordLiveData: MutableLiveData<List<Word>> = MutableLiveData()

    /**
     * 批量插入文章数据
     */
    fun insertArticles(articles: List<Article>) {
        viewModelScope.launch {
            readerDao.insert(articles)
        }
    }

    /**
     * 批量插入单词数据
     */
    fun insertWords(words: List<Word>) {
        viewModelScope.launch {
            wordDao.insert(words)
        }
    }

    /**
     * 获取所有文章数据
     */
    fun getAllArticle() {
        viewModelScope.launch(Dispatchers.IO) {
            articleLiveData.postValue(readerDao.getAllArticle())
        }
    }

    /**
     * 获取所有单词数据
     */
    fun getAllWord() {
        viewModelScope.launch(Dispatchers.IO) {
            wordLiveData.postValue(wordDao.getAllWord())
        }
    }
}