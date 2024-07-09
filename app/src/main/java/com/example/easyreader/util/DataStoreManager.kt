package com.example.easyreader.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * 数据存储管理类
 */
class DataStoreManager(context: Context) {

    private val Context.dataStore: DataStore<Preferences> by
  			preferencesDataStore(name = "user_sp")//文件名称

  	private val dataStore = context.dataStore

    companion object {
        val PREF_STORED = booleanPreferencesKey("stored")
    }

    /**
     * 写入数据
     * @param key
     * @param value
     */
    suspend fun setValue(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { pref ->
            pref[key] = value
        }
    }

    fun getValue(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return dataStore.data.catch { exception ->
            //如果抛出IO异常，返回一个空的首选项
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { pref ->
            //根据 key 读取数据存入变量
            val name = pref[key] ?: false
            name
        }
    }


}
