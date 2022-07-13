package com.canbazdev.rickandmortyapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.canbazdev.rickandmortyapp.data.model.characters.CharacterDetail
import com.canbazdev.rickandmortyapp.data.model.episodes.Episode
import com.canbazdev.rickandmortyapp.data.model.locations.Location
import com.canbazdev.rickandmortyapp.data.model.locations.LocationsResponse
import com.canbazdev.rickandmortyapp.data.remote.api.RickAndMortyService
import com.canbazdev.rickandmortyapp.data.remote.api.paging_sources.CharactersFilterPagingDataSource
import com.canbazdev.rickandmortyapp.data.remote.api.paging_sources.CharactersPagingDataSource
import com.canbazdev.rickandmortyapp.data.remote.api.paging_sources.EpisodesPagingDataSource
import com.canbazdev.rickandmortyapp.data.remote.api.paging_sources.LocationsPagingDataSource
import com.canbazdev.rickandmortyapp.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 20.06.2022
*/
class RickAndMortyRepositoryImpl @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
) : RickAndMortyRepository {
    override suspend fun getCharacters(): Flow<PagingData<CharacterDetail>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                CharactersPagingDataSource(
                    rickAndMortyService
                )
            }
        ).flow
    }

    override suspend fun getMultipleCharacters(idList: String): ArrayList<CharacterDetail> =
        rickAndMortyService.getMultipleCharacters(idList)

    override suspend fun getMultipleLocations(idList: String): ArrayList<Location> =
        rickAndMortyService.getMultipleLocations(idList)

    override suspend fun getFilterCharacters(filter: Map<String, String>): Flow<PagingData<CharacterDetail>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                CharactersFilterPagingDataSource(
                    rickAndMortyService,
                    filter
                )
            }
        ).flow

    }

    override suspend fun getCharacterDetailById(characterId: String): CharacterDetail =
        rickAndMortyService.getCharacterDetailById(characterId)

    //    override suspend fun getLocations(): LocationsResponse = rickAndMortyService.getLocations()
    override suspend fun getLocations(): Flow<PagingData<Location>> {
        return Pager(
            config = PagingConfig(pageSize = 7),
            pagingSourceFactory = {
                LocationsPagingDataSource(
                    rickAndMortyService
                )
            }
        ).flow
    }

    override suspend fun getLocationIdList(): LocationsResponse =
        rickAndMortyService.getLocationIdList()

    override suspend fun getEpisodes(): Flow<PagingData<Episode>> {
        return Pager(
            config = PagingConfig(pageSize = 3),
            pagingSourceFactory = {
                EpisodesPagingDataSource(
                    rickAndMortyService
                )
            }
        ).flow
    }


}