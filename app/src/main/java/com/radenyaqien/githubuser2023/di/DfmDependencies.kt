package com.radenyaqien.githubuser2023.di

import com.radenyaqien.core.domain.Repository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DfmDependencies {

    fun provideRepository(): Repository
}