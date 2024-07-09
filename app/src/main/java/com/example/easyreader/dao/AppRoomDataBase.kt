package com.example.easyreader.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.easyreader.dao.data.Article
import com.example.easyreader.dao.data.Word

@Database(entities = [Article::class, Word::class], version = 1, exportSchema = false)
abstract class AppRoomDataBase : RoomDatabase() {

    abstract fun readerDao(): ReaderDao
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDataBase? = null
        fun getDatabase(context: Context): AppRoomDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDataBase::class.java,
                    "reader_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}