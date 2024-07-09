package com.example.easyreader

import android.app.Application
import android.content.res.AssetManager
import com.example.easyreader.dao.AppRoomDataBase
import com.example.easyreader.dao.data.Article
import com.example.easyreader.dao.data.Word
import com.example.easyreader.util.DataStoreManager
import com.example.easyreader.viewmodel.ReaderViewModel
import com.example.easyreader.viewmodel.ReaderViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class EasyReaderApplication : Application() {

    val database: AppRoomDataBase by lazy { AppRoomDataBase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        val dataStore = DataStoreManager(this)
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.getValue(DataStoreManager.PREF_STORED).collect { stored ->
                if (!stored) {
                    val viewModel =
                        ReaderViewModelFactory(database.readerDao(), database.wordDao()).create(
                            ReaderViewModel::class.java
                        )
                    file2JsonStr("Articles.json")?.let {
                        viewModel.insertArticles(jsonStr2ArticleList(it))
                    }
                    file2JsonStr("Words.json")?.let {
                        viewModel.insertWords(jsonStr2WordList(it))
                    }
                    dataStore.setValue(DataStoreManager.PREF_STORED, true)
                }
            }
        }
    }

    /**
     * 从文件中读取内容转成json字符串
     */
    private fun file2JsonStr(fileName: String): String? {
        val stringBuilder = StringBuilder()
        try {
            val assetManager: AssetManager = this.assets

            val isr = InputStreamReader(assetManager.open(fileName))
            val bf = BufferedReader(isr)
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            bf.close()
            isr.close()
            return stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * json字符串转文章列表
     */
    private fun jsonStr2ArticleList(jsonStr: String): List<Article> {
        val type = object : TypeToken<List<Article>>() {}.type
        val list = Gson().fromJson<List<Article>>(jsonStr, type)
        return list
    }

    /**
     * json字符串转单词列表
     */
    private fun jsonStr2WordList(jsonStr: String): List<Word> {
        val type = object : TypeToken<List<Word>>() {}.type
        val list = Gson().fromJson<List<Word>>(jsonStr, type)
        return list
    }

}