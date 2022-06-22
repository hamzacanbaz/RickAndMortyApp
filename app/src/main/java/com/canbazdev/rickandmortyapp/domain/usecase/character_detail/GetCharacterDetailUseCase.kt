package com.canbazdev.rickandmortyapp.domain.usecase.character_detail

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
*   Created by hamzacanbaz on 21.06.2022
*/
class GetCharacterDetailUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {
    operator fun invoke(characterId: String): Flow<Resource<Character>> = flow {
        try {
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    rickAndMortyRepository.getCharacterDetailById(characterId).toCharacter()
                )
            )
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }
}