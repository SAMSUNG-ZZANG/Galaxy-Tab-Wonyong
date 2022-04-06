package com.example.sopt_seminar.di

import android.content.Context
import com.example.sopt_seminar.data.repository.UserRepository
import com.example.sopt_seminar.data.repository.UserRepositoryImpl
import com.example.sopt_seminar.data.source.local.UserLocalDatSource
import com.example.sopt_seminar.data.source.local.UserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Provides
    @Singleton
    fun provideUserLocalDataSource(@ApplicationContext context: Context): UserLocalDatSource {
        return UserLocalDataSourceImpl(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userLocalDatSource:UserLocalDatSource): UserRepository {
        return UserRepositoryImpl(userLocalDatSource)
    }
}