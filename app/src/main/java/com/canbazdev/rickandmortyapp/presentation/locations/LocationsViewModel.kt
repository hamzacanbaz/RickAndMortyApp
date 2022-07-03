package com.canbazdev.rickandmortyapp.presentation.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.model.Location
import com.canbazdev.rickandmortyapp.domain.usecase.character_detail.GetCharacterDetailUseCase
import com.canbazdev.rickandmortyapp.domain.usecase.locations.GetLocationsUseCase
import com.canbazdev.rickandmortyapp.util.Event
import com.canbazdev.rickandmortyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 23.06.2022
*/
@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : ViewModel(), LocationsAdapter.OnItemClickedListener {

    private val _locations = MutableStateFlow<List<Location>>(listOf())
    val locations: StateFlow<List<Location>> = _locations

    private val _uiState = MutableStateFlow(0)
    val uiState: StateFlow<Int> = _uiState

    private val _openTheLocationDetails = MutableStateFlow(false)
    private val openTheLocationDetails: StateFlow<Boolean> = _openTheLocationDetails

    private val _locationPosition = MutableStateFlow(0)
    private val locationPosition: StateFlow<Int> = _locationPosition

    private val _nestedCharactersList = MutableStateFlow<ArrayList<Character>>(arrayListOf())
    val nestedCharactersList: StateFlow<ArrayList<Character>> = _nestedCharactersList

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val map = mapOf("8" to "Hamza", "14" to "Ahmet", "3" to "Esma")


    init {
        getLocations()
        openTheLocationDetails()
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

    private fun openTheLocationDetails() = viewModelScope.launch {
        delay(1000)
        openTheLocationDetails.collect {
            if (it) {
                _openTheLocationDetails.value = false
                eventChannel.send(Event.OpenTheLocationDetail(locationPosition.value))
            }
        }
    }

    // TODO burada tüm response'lar gelmeden döndürme işlemi yapma
    override fun onItemClicked(position: Int, idList: List<String>): ArrayList<Character> {
        _nestedCharactersList.value.clear()
        idList.let { list ->
            list.forEach { id ->
                getCharacterDetailUseCase(id.last().toString()).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { character ->
                                println("character name:" + character.name)
                                _nestedCharactersList.value.add(character)
                                println("character size:" + nestedCharactersList.value.size)
                            }
                        }
                        is Resource.Loading -> {
                            _nestedCharactersList.value.add(Character(name = "Test"))
                            println("loading")
                        }
                        is Resource.Error -> {
                            println("error")
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
        println("value" + nestedCharactersList.value)
        return nestedCharactersList.value

    }

}

