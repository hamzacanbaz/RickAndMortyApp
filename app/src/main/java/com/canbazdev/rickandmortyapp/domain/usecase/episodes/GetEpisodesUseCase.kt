package com.canbazdev.rickandmortyapp.domain.usecase.episodes

import com.canbazdev.rickandmortyapp.data.remote.model.episodes.toEpisode
import com.canbazdev.rickandmortyapp.domain.model.Episode
import com.canbazdev.rickandmortyapp.domain.repository.RickAndMortyRepository
import com.canbazdev.rickandmortyapp.util.Resource
import kotlinx.coroutines.flow.Flow
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
    operator fun invoke(): Flow<Resource<List<Episode>>> = flow {
        try {
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    rickAndMortyRepository.getEpisodes().results.map { it.toEpisode() })
            )
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }
}