package com.canbazdev.rickandmortyapp.di

import com.canbazdev.rickandmortyapp.data.remote.api.RickAndMortyService
import com.canbazdev.rickandmortyapp.data.repository.RickAndMortyRepositoryImpl
import com.canbazdev.rickandmortyapp.domain.repository.RickAndMortyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
*   Created by hamzacanbaz on 20.06.2022
*/
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRickAndMortyRepository(rickAndMortyService: RickAndMortyService): RickAndMortyRepository {
        return RickAndMortyRepositoryImpl(rickAndMortyService)
    }
}