package com.canbazdev.rickandmortyapp.data.remote.api

import com.canbazdev.rickandmortyapp.data.remote.model.characters.CharacterDetail
import com.canbazdev.rickandmortyapp.data.remote.model.characters.CharactersResponse
import com.canbazdev.rickandmortyapp.data.remote.model.episodes.EpisodesResponse
import com.canbazdev.rickandmortyapp.data.remote.model.locations.LocationsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/*
*   Created by hamzacanbaz on 19.06.2022
*/
interface RickAndMortyService {
    @GET("character")
    suspend fun getCharacters(): CharactersResponse

    @GET("character")
    suspend fun getFilterCharacters(
        @QueryMap filter: Map<String, String>
    ): CharactersResponse

    @GET("character/{characterId}")
    suspend fun getCharacterDetailById(@Path("characterId") characterId: String): CharacterDetail

    @GET("location")
    suspend fun getLocations(): LocationsResponse

    @GET("episode")
    suspend fun getEpisodes(): EpisodesResponse
}
