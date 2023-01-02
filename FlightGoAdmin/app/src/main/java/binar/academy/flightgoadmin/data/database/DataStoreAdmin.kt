package binar.academy.flightgoadmin.data.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import binar.academy.flightgoadmin.data.database.DataStoreAdmin.Companion.token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.token: DataStore<Preferences> by preferencesDataStore(name = token)

class DataStoreAdmin(val context: Context) {
    val DATA_ROLE = stringPreferencesKey(ROLE)
    val DATA_ISLOGIN = booleanPreferencesKey(IS_LOGIN)
    private val MYTOKEN = stringPreferencesKey(token)

    suspend fun saveData(paramRole: String, paramToken: String) {
        context.token.edit {
            it[DATA_ROLE] = paramRole
            it[MYTOKEN] = paramToken
        }
    }

    fun getRole(): Flow<String> = context.token.data.map {
        it[DATA_ROLE] ?: ""
    }

    fun getIsLogin(): Flow<Boolean> = context.token.data.map {
        it[DATA_ISLOGIN] ?: false
    }

    fun getToken(): Flow<String> = context.token.data.map {
        it[MYTOKEN] ?: "undefined"
    }


    @Suppress("RedundantSuspendModifier")

    suspend fun deleteToken() = context.token.edit {
        it.clear()
    }

    suspend fun setLogin(paramIsLogin: Boolean) {
        context.token.edit {
            it[DATA_ISLOGIN] = paramIsLogin
        }
    }

    suspend fun removeLogin() {
        context.token.edit {

        }
    }

    companion object {
        const val ROLE = "role"
        const val IS_LOGIN = "isLogin"
        const val token = "token"
    }
}