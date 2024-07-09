package com.example.easyreader.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easyreader.dao.data.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Word) // 插入数据

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<Word>) // 批量插入数据

    @Query("SELECT * from words WHERE id = :id") //根据id查询单条数据
    fun getWord(id: Int): Flow<Word>

    @Query("SELECT * FROM words")
    fun getAllWord(): MutableList<Word> //获取所有数据
}