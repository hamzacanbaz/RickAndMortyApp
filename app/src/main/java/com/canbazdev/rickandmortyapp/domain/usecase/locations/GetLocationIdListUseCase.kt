package com.canbazdev.rickandmortyapp.domain.usecase.locations

import com.canbazdev.rickandmortyapp.data.model.locations.toLocation
import com.canbazdev.rickandmortyapp.domain.model.Location
import com.canbazdev.rickandmortyapp.domain.repository.RickAndMortyRepository
import com.canbazdev.rickandmortyapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 23.06.2022
*/
class GetLocationIdListUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {
    operator fun invoke(pageNumber: Int? = null): Flow<Resource<List<Location>>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(rickAndMortyRepository.getLocationIdList().results.map {
                it.toLocation() }))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }
}