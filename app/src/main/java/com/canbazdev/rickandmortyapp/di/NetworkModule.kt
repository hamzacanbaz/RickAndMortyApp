package com.canbazdev.rickandmortyapp.di

import com.canbazdev.rickandmortyapp.data.remote.api.RickAndMortyService
import com.canbazdev.rickandmortyapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
*   Created by hamzacanbaz on 19.06.2022
*/
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRickAndMortyService(): RickAndMortyService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickAndMortyService::class.java)
    }
}
