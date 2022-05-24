package com.example.sopt_seminar.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.sopt_seminar.data.constants.DATASTORE
import com.example.sopt_seminar.data.constants.IS_AUTO_LOGIN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserLocalDataSourceImpl @Inject constructor(
    @ApplicationContext val context: Context
) : UserLocalDatSource {
    private val Context.dataStore by preferencesDataStore(name = DATASTORE)
    private val isAutoLoginKey = booleanPreferencesKey(IS_AUTO_LOGIN)

    override suspend fun isAutoLogin(): Flow<Boolean> {
        val isAutoLogin: Flow<Boolean> = context.dataStore.data.map { preferences ->
            preferences[isAutoLoginKey] ?: false
        }
        return isAutoLogin
    }

    override suspend fun setAutoLogin(isAutoLogin: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[isAutoLoginKey] = isAutoLogin
        }
    }
}