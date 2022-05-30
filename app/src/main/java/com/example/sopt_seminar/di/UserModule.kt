package com.example.sopt_seminar.di

import android.content.Context
import com.example.sopt_seminar.data.api.ApiService
import com.example.sopt_seminar.data.repository.UserRepositoryImpl
import com.example.sopt_seminar.data.source.local.UserLocalDatSource
import com.example.sopt_seminar.data.source.local.UserLocalDataSourceImpl
import com.example.sopt_seminar.data.source.remote.UserRemoteDataSource
import com.example.sopt_seminar.data.source.remote.UserRemoteDataSourceImpl
import com.example.sopt_seminar.domain.repository.UserRepository
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
    fun provideUserRemoteDataSource(
        @BaseApi baseApiService: ApiService,
        @GithubApi githubApiService: ApiService
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(baseApiService, githubApiService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userLocalDatSource: UserLocalDatSource,
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository {
        return UserRepositoryImpl(userLocalDatSource, userRemoteDataSource)
    }
}