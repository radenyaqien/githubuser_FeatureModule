package com.radenyaqien.core.di


import com.radenyaqien.core.data.remote.GithubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGithubInterface(retrofit: Retrofit): GithubApiService {
        return retrofit.create(GithubApiService::class.java)
    }


}