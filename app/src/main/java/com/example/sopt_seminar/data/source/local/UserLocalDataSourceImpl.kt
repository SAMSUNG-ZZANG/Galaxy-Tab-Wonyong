package com.example.sopt_seminar.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sopt_seminar.data.constants.DATASTORE
import com.example.sopt_seminar.data.constants.GET_USER_ID
import com.example.sopt_seminar.data.constants.GET_USER_NAME
import com.example.sopt_seminar.data.constants.GET_USER_PASSWORD
import com.example.sopt_seminar.data.entity.UserEntity
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

    override suspend fun getUser(): Flow<UserEntity> {
        val userId: Flow<UserEntity> = context.dataStore.data.map { preferences ->
            val id = preferences[userIdKey] ?: ""
            val password = preferences[userPasswordKey] ?: ""
            val name = preferences[userNameKey] ?: ""
            UserEntity(name, id, password)
        }
        return userId
    }

    override suspend fun setUser(user: UserEntity) {
        context.dataStore.edit { preferences ->
            preferences[userIdKey] = user.userId
            preferences[userPasswordKey] = user.userPassword
            preferences[userNameKey] = user.userName
        }
    }
}