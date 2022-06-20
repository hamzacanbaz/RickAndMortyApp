package com.canbazdev.rickandmortyapp.data.repository

import com.canbazdev.rickandmortyapp.data.remote.api.RickAndMortyService
import com.canbazdev.rickandmortyapp.data.remote.model.characters.CharactersResponse
import com.canbazdev.rickandmortyapp.domain.repository.RickAndMortyRepository
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 20.06.2022
*/
class RickAndMortyRepositoryImpl @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
) : RickAndMortyRepository {
    override suspend fun getCharacters(): CharactersResponse = rickAndMortyService.getCharacters()

}