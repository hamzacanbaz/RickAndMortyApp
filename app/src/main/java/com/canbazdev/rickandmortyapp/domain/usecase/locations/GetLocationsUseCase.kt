package com.canbazdev.rickandmortyapp.domain.usecase.locations

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.canbazdev.rickandmortyapp.data.model.locations.toLocation
import com.canbazdev.rickandmortyapp.domain.model.Location
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
*   Created by hamzacanbaz on 23.06.2022
*/
class GetLocationsUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {
    operator fun invoke(
        pageNumber: Int? = null,
        coroutineScope: CoroutineScope
    ): Flow<Resource<PagingData<Location>>> = flow {
        try {
            emit(Resource.Loading())
            rickAndMortyRepository.getLocations().cachedIn(coroutineScope).collect {
                emit(Resource.Success(it.map { location ->
                    location.toLocation()
                }))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }
}