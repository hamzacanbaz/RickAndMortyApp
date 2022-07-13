package com.canbazdev.rickandmortyapp.domain.usecase.episodes

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.canbazdev.rickandmortyapp.data.model.episodes.toEpisode
import com.canbazdev.rickandmortyapp.domain.model.Episode
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
*   Created by hamzacanbaz on 8.07.2022
*/
class GetEpisodesUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {
    operator fun invoke(
        pageNumber: Int? = null,
        coroutineScope: CoroutineScope
    ): Flow<Resource<PagingData<Episode>>> = flow {
        try {
            emit(Resource.Loading())
            rickAndMortyRepository.getEpisodes().cachedIn(coroutineScope).collect {
                emit(Resource.Success(it.map { episode ->
                    episode.toEpisode()
                }))
            }


        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }
}