package com.example.easyreader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easyreader.dao.ReaderDao
import com.example.easyreader.dao.WordDao

class ReaderViewModelFactory(private val readerDao: ReaderDao, private val wordDao: WordDao) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReaderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReaderViewModel(readerDao, wordDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}