package com.example.easyreader.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easyreader.dao.data.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ReaderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Article) // 插入数据

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<Article>) // 批量插入数据

    @Query("SELECT * from articles WHERE id = :id") //根据id查询单条数据
    fun getArticle(id: Int): Flow<Article>

    @Query("SELECT * FROM articles")
    fun getAllArticle(): MutableList<Article> //获取所有数据

}