package com.example.sopt_seminar.di

import com.example.sopt_seminar.data.api.ApiService
import com.example.sopt_seminar.data.constants.BASE_URL
import com.example.sopt_seminar.data.constants.GITHUB_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Qualifier
annotation class GithubApi

@Qualifier
annotation class BaseApi

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @BaseApi
    @Provides
    fun provideBaseApiRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @GithubApi
    @Provides
    fun provideGitHubRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @BaseApi
    @Provides
    fun provideAuthBaseApi(@BaseApi retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @GithubApi
    @Provides
    fun provideAuthGithubApi(@GithubApi retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}