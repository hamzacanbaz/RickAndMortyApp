package com.canbazdev.rickandmortyapp.presentation.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.rickandmortyapp.domain.model.Location
import com.canbazdev.rickandmortyapp.domain.usecase.locations.GetLocationsUseCase
import com.canbazdev.rickandmortyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 23.06.2022
*/
@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {

    private val _locations = MutableStateFlow<List<Location>>(listOf())
    val locations: StateFlow<List<Location>> = _locations

    private val _uiState = MutableStateFlow(0)
    val uiState: StateFlow<Int> = _uiState

    init {
        getLocations()
    }

    private fun getLocations() {
        getLocationsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { list ->
                        _locations.value = list
                        _uiState.value = 1
                    }
                }
                is Resource.Error -> {
                    _uiState.value = -1
                }
                is Resource.Loading -> {
                    _uiState.value = 0
                }
            }
        }.launchIn(viewModelScope)
    }

}

