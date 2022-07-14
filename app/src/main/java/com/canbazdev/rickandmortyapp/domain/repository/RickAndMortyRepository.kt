package com.canbazdev.rickandmortyapp.domain.repository

import androidx.paging.PagingData
import com.canbazdev.rickandmortyapp.data.model.characters.CharacterDetail
import com.canbazdev.rickandmortyapp.data.model.episodes.Episode
import com.canbazdev.rickandmortyapp.data.model.locations.Location
import com.canbazdev.rickandmortyapp.data.model.locations.LocationsResponse
import kotlinx.coroutines.flow.Flow

/*
*   Created by hamzacanbaz on 20.06.2022
*/
interface RickAndMortyRepository {
    suspend fun getCharacters(): Flow<PagingData<CharacterDetail>>
    suspend fun getMultipleCharacters(idList: String): ArrayList<CharacterDetail>
    suspend fun getMultipleLocations(idList: String): ArrayList<Location>
    suspend fun getFilterCharacters(
        filter: Map<String, String>
    ): Flow<PagingData<CharacterDetail>>

    suspend fun getCharacterDetailById(characterId: String): CharacterDetail
    suspend fun getLocations(): Flow<PagingData<Location>>
    suspend fun getLocationIdList(page:Int?): LocationsResponse
    suspend fun getEpisodes(): Flow<PagingData<Episode>>
}