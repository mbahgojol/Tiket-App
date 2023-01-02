package com.rzl.flightgotiketbooking.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.prefDataStore by preferencesDataStore("flight-use")

class SharedPref constructor(appContext: Context) {

    private val dataStore = appContext.prefDataStore
    private val tokenKEY = stringPreferencesKey("token")

    fun getToken(): Flow<String> = dataStore.data.map {
        it[tokenKEY] ?: ""
    }

    suspend fun saveToken(token: String) {
        dataStore.edit {
            it[tokenKEY] = token
        }
    }
}

fun String.addBearer() = "Bearer $this"