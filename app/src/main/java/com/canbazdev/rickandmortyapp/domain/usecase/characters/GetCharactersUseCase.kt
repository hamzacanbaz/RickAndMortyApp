package com.canbazdev.rickandmortyapp.domain.usecase.characters

import com.canbazdev.rickandmortyapp.data.remote.model.characters.toCharacter
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.repository.RickAndMortyRepository
import com.canbazdev.rickandmortyapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 20.06.2022
*/
class GetCharactersUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {
    operator fun invoke(): Flow<Resource<List<Character>>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(rickAndMortyRepository.getCharacters().results.map { it.toCharacter() }))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }
}