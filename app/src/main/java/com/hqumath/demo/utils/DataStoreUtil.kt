package com.hqumath.demo.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

// 绑定 DataStore 实例到 Context
private val Context.dataStore by preferencesDataStore(name = "settings")

object DataStoreUtil {

    private val dataStore = CommonUtil.getContext().dataStore

    fun <T> putData(key: String, value: T) {
        runBlocking {
            when (value) {
                is Int -> putIntData(key, value)
                is Long -> putLongData(key, value)
                is String -> putStringData(key, value)
                is Boolean -> putBooleanData(key, value)
                is Float -> putFloatData(key, value)
                is Double -> putDoubleData(key, value)
                else -> {}
            }
        }
    }

    fun <T> getData(key: String, default: T): T {
        val data = when (default) {
            is Int -> getIntData(key, default)
            is Long -> getLongData(key, default)
            is String -> getStringData(key, default)
            is Boolean -> getBooleanData(key, default)
            is Float -> getFloatData(key, default)
            is Double -> getDoubleData(key, default)
            else -> {}
        }
        return data as T
    }

    fun clearAllData() {
        runBlocking {
            clearData()
        }
    }

    /**
     * 清空所有数据
     */
    private suspend fun clearData() {
        dataStore.edit { it.clear() }
    }

    /**
     * 存Int数据
     */
    private suspend fun putIntData(key: String, value: Int) =
        dataStore.edit { it[intPreferencesKey(key)] = value }

    /**
     * 存Long数据
     */
    private suspend fun putLongData(key: String, value: Long) =
        dataStore.edit { it[longPreferencesKey(key)] = value }

    /**
     * 存String数据
     */
    private suspend fun putStringData(key: String, value: String) =
        dataStore.edit { it[stringPreferencesKey(key)] = value }

    /**
     * 存Boolean数据
     */
    private suspend fun putBooleanData(key: String, value: Boolean) =
        dataStore.edit { it[booleanPreferencesKey(key)] = value }

    /**
     * 存Float数据
     */
    private suspend fun putFloatData(key: String, value: Float) =
        dataStore.edit { it[floatPreferencesKey(key)] = value }

    /**
     * 存Double数据
     */
    private suspend fun putDoubleData(key: String, value: Double) =
        dataStore.edit { it[doublePreferencesKey(key)] = value }

    /**
     * 获取Int数据
     */
    private fun getIntData(key: String, default: Int = 0) = runBlocking {
        return@runBlocking dataStore.data.map { it[intPreferencesKey(key)] ?: default }.first()
    }

    /**
     * 获取Long数据
     */
    private fun getLongData(key: String, default: Long = 0) = runBlocking {
        return@runBlocking dataStore.data.map { it[longPreferencesKey(key)] ?: default }.first()
    }

    /**
     * 获取String数据
     */
    private fun getStringData(key: String, default: String? = null) = runBlocking {
        return@runBlocking dataStore.data.map { it[stringPreferencesKey(key)] ?: default }.first()
    }

    /**
     * 获取Boolean数据
     */
    private fun getBooleanData(key: String, default: Boolean = false) = runBlocking {
        return@runBlocking dataStore.data.map { it[booleanPreferencesKey(key)] ?: default }.first()
    }

    /**
     * 获取Float数据
     */
    private fun getFloatData(key: String, default: Float = 0.0f) = runBlocking {
        return@runBlocking dataStore.data.map { it[floatPreferencesKey(key)] ?: default }.first()
    }

    /**
     * 获取Double数据
     */
    private fun getDoubleData(key: String, default: Double = 0.00) = runBlocking {
        return@runBlocking dataStore.data.map { it[doublePreferencesKey(key)] ?: default }.first()
    }
}