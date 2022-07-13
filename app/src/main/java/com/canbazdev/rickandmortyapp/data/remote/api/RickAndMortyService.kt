package com.canbazdev.rickandmortyapp.data.remote.api

import com.canbazdev.rickandmortyapp.data.model.characters.CharacterDetail
import com.canbazdev.rickandmortyapp.data.model.characters.CharactersResponse
import com.canbazdev.rickandmortyapp.data.model.episodes.EpisodesResponse
import com.canbazdev.rickandmortyapp.data.model.locations.Location
import com.canbazdev.rickandmortyapp.data.model.locations.LocationsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/*
*   Created by hamzacanbaz on 19.06.2022
*/
interface RickAndMortyService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int? = null): CharactersResponse

    @GET("character/{idList}")
    suspend fun getMultipleCharacters(@Path("idList") idList: String): ArrayList<CharacterDetail>

    @GET("location/{idList}")
    suspend fun getMultipleLocations(@Path("idList") idList: String): ArrayList<Location>

    @GET("character")
    suspend fun getFilterCharacters(
        @Query("page") page: Int? = null,
        @QueryMap filter: Map<String, String>
    ): CharactersResponse

    @GET("character/{characterId}")
    suspend fun getCharacterDetailById(@Path("characterId") characterId: String): CharacterDetail

    @GET("location")
    suspend fun getLocations(@Query("page") page: Int? = null): LocationsResponse

    @GET("location")
    suspend fun getLocationIdList(@Query("page") page: Int? = null): LocationsResponse

    @GET("episode")
    suspend fun getEpisodes(@Query("page") page: Int? = null): EpisodesResponse
}
