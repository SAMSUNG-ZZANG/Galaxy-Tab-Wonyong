package com.example.sopt_seminar.di

import com.example.sopt_seminar.data.repository.UserRepositoryImpl
import com.example.sopt_seminar.data.source.local.UserLocalDatSource
import com.example.sopt_seminar.data.source.local.UserLocalDataSourceImpl
import com.example.sopt_seminar.data.source.remote.UserRemoteDataSource
import com.example.sopt_seminar.data.source.remote.UserRemoteDataSourceImpl
import com.example.sopt_seminar.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Provides
    @Singleton
    fun provideUserLocalDataSource(
        impl: UserLocalDataSourceImpl
    ): UserLocalDatSource = impl

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        impl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource = impl

    @Provides
    @Singleton
    fun provideUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository = impl

}