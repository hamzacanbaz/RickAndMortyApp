package com.canbazdev.rickandmortyapp.domain.repository

import com.canbazdev.rickandmortyapp.data.remote.model.characters.CharactersResponse

/*
*   Created by hamzacanbaz on 20.06.2022
*/
interface RickAndMortyRepository {
    suspend fun getCharacters(): CharactersResponse
}