package com.example.sopt_seminar.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sopt_seminar.data.constants.DATASTORE
import com.example.sopt_seminar.data.constants.GET_USER_ID
import com.example.sopt_seminar.data.constants.GET_USER_NAME
import com.example.sopt_seminar.data.constants.GET_USER_PASSWORD
import com.example.sopt_seminar.data.constants.IS_USER
import com.example.sopt_seminar.data.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserLocalDataSourceImpl @Inject constructor(
    @ApplicationContext val context: Context
) : UserLocalDatSource {
    private val Context.dataStore by preferencesDataStore(name = DATASTORE)
    private val userIdKey = stringPreferencesKey(GET_USER_ID)
    private val userPasswordKey = stringPreferencesKey(GET_USER_PASSWORD)
    private val userNameKey = stringPreferencesKey(GET_USER_NAME)
    private val isUserKey = booleanPreferencesKey(IS_USER)

    override fun getUser(): Flow<User> {
        val userId: Flow<User> = context.dataStore.data.map { preferences ->
            val id = preferences[userIdKey] ?: ""
            val password = preferences[userPasswordKey] ?: ""
            val name = preferences[userNameKey] ?: ""
            User(name, id, password)
        }
        return userId
    }

    override suspend fun setUser(user: User) {
        context.dataStore.edit { preferences ->
            preferences[userIdKey] = user.userId
            preferences[userPasswordKey] = user.userPassword
            preferences[userNameKey] = user.userName
        }
    }

    override fun getIsUser(): Flow<Boolean> {
        val isUser: Flow<Boolean> = context.dataStore.data.map { preferences ->
            preferences[isUserKey] ?: false
        }
        return isUser
    }

    override suspend fun setIsUser() {
        context.dataStore.edit { preferences ->
            preferences[isUserKey] = true
        }
    }
}