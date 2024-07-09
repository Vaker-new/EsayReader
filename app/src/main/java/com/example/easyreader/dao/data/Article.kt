package com.example.easyreader.dao.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val titleZh: String,
    val content: String,
    val contentZh: String,
): Serializable
