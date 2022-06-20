package com.canbazdev.rickandmortyapp.data.remote.api

import com.canbazdev.rickandmortyapp.data.remote.model.characters.CharactersResponse
import retrofit2.http.GET

/*
*   Created by hamzacanbaz on 19.06.2022
*/
interface RickAndMortyService {
    @GET("character")
    suspend fun getCharacters(): CharactersResponse
}