package com.canbazdev.rickandmortyapp.domain.usecase.characters

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.canbazdev.rickandmortyapp.data.model.characters.toCharacter
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.repository.RickAndMortyRepository
import com.canbazdev.rickandmortyapp.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 06.07.2022
*/
class GetFilterCharactersUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {
    operator fun invoke(
        filter: Map<String, String>,
        coroutineScope: CoroutineScope
    ): Flow<Resource<PagingData<Character>>> = flow {
        try {
            emit(Resource.Loading())
            rickAndMortyRepository.getFilterCharacters(filter).cachedIn(coroutineScope).collect {
                emit(Resource.Success(it.map { cd ->
                    cd.toCharacter()
                }))
            }

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }
}