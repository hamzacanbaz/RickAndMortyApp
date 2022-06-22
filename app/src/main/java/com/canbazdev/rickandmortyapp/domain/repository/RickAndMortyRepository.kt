package com.canbazdev.rickandmortyapp.domain.repository

import com.canbazdev.rickandmortyapp.data.remote.model.characters.CharacterDetail
import com.canbazdev.rickandmortyapp.data.remote.model.characters.CharactersResponse
import com.canbazdev.rickandmortyapp.data.remote.model.locations.LocationsResponse

/*
*   Created by hamzacanbaz on 20.06.2022
*/
interface RickAndMortyRepository {
    suspend fun getCharacters(): CharactersResponse
    suspend fun getCharacterDetailById(characterId: String): CharacterDetail
    suspend fun getLocations(): LocationsResponse
}