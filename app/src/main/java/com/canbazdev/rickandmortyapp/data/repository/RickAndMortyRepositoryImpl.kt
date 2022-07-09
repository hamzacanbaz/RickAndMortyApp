package com.canbazdev.rickandmortyapp.data.repository

import com.canbazdev.rickandmortyapp.data.remote.api.RickAndMortyService
import com.canbazdev.rickandmortyapp.data.remote.model.characters.CharacterDetail
import com.canbazdev.rickandmortyapp.data.remote.model.characters.CharactersResponse
import com.canbazdev.rickandmortyapp.data.remote.model.episodes.EpisodesResponse
import com.canbazdev.rickandmortyapp.data.remote.model.locations.LocationsResponse
import com.canbazdev.rickandmortyapp.domain.repository.RickAndMortyRepository
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 20.06.2022
*/
class RickAndMortyRepositoryImpl @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
) : RickAndMortyRepository {
    override suspend fun getCharacters(): CharactersResponse = rickAndMortyService.getCharacters()
    override suspend fun getFilterCharacters(filter: Map<String, String>): CharactersResponse =
        rickAndMortyService.getFilterCharacters(filter)

    override suspend fun getCharacterDetailById(characterId: String): CharacterDetail =
        rickAndMortyService.getCharacterDetailById(characterId)

    override suspend fun getLocations(): LocationsResponse = rickAndMortyService.getLocations()
    override suspend fun getEpisodes(): EpisodesResponse = rickAndMortyService.getEpisodes()


}