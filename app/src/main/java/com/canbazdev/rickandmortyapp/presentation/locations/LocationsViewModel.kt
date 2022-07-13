package com.canbazdev.rickandmortyapp.presentation.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.model.Location
import com.canbazdev.rickandmortyapp.domain.usecase.character_detail.GetCharacterDetailUseCase
import com.canbazdev.rickandmortyapp.domain.usecase.characters.GetMultipleCharactersUseCase
import com.canbazdev.rickandmortyapp.domain.usecase.locations.GetLocationIdListUseCase
import com.canbazdev.rickandmortyapp.domain.usecase.locations.GetLocationsUseCase
import com.canbazdev.rickandmortyapp.util.Event
import com.canbazdev.rickandmortyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 23.06.2022
*/
@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val getMultipleCharactersUseCase: GetMultipleCharactersUseCase,
    private val getLocationIdListUseCase: GetLocationIdListUseCase
) : ViewModel(), LocationsAdapter.OnItemClickedListener {

    private val _locations = MutableStateFlow<PagingData<Location>>(PagingData.empty())
    val locations: StateFlow<PagingData<Location>> = _locations

    private val _chars = MutableStateFlow<ArrayList<Pair<String, List<Character>>>>(arrayListOf())
    val chars: StateFlow<ArrayList<Pair<String, List<Character>>>> = _chars

    private val _uiState = MutableStateFlow(0)
    val uiState: StateFlow<Int> = _uiState

    private val _openTheLocationDetails = MutableStateFlow(false)
    private val openTheLocationDetails: StateFlow<Boolean> = _openTheLocationDetails

    private val _locationPosition = MutableStateFlow(0)
    private val locationPosition: StateFlow<Int> = _locationPosition

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val _idList = MutableStateFlow("")
    private val idList: StateFlow<String> = _idList


    init {
        getLocationsIdList()
        getLocations()
        openTheLocationDetails()
    }



    private fun getLocations() {
        getLocationsUseCase(coroutineScope = viewModelScope).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { list ->
                        _locations.value = list

//                        parseIdList(list)
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

    private fun getLocationsIdList() {
        getLocationIdListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { list ->
                        parseIdList(list)
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
        openTheLocationDetails.collect {
            if (it) {
                _openTheLocationDetails.value = false
                eventChannel.send(Event.OpenTheLocationDetail(locationPosition.value))
            }
        }
    }

    override fun onItemClicked(
        position: Int,
        idList: List<String>,
        locationName: String
    ): List<Character> {
//        _idList.value = ""
//        if (idList.isNotEmpty()) {
//            idList.forEach { charLink ->
//                _idList.value += charLink.substringAfter("character/") + ","
//            }
//            _idList.value = _idList.value.substring(0, idList.size - 1)
//
//        }
//        getEpisodeCharacters(locationName)


        chars.value.forEach {
            if (it.first == locationName) {
                return it.second
            }
        }
        return emptyList()
    }

    private fun getEpisodeCharacters(locationName: String) {
//        _nestedCharactersList.value = listOf()
        viewModelScope.launch {
            if (idList.value.contains(",")) {
                getMultipleCharactersUseCase.invoke(idList.value).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { characterList ->
                                _chars.value.add(Pair(locationName, characterList))
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
                }
            } else {
                getCharacterDetailUseCase.invoke(idList.value).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { characterList ->
                                _chars.value.add(Pair(locationName, listOf(characterList)))
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
                }
            }
        }
    }

    fun parseIdList(list: List<Location>) {
        val locationsList = arrayListOf<Location>()
        list.map {
            locationsList.add(it)
        }
        locationsList.forEach {
            val list = it.residents
            val locationName = it.name
            _idList.value = ""
            if (list?.isNotEmpty() == true) {
                list.forEach { charLink ->
                    _idList.value += charLink.substringAfter("character/") + ","
                }
                _idList.value = _idList.value.substring(0, idList.value.length - 1)

            }
            locationName?.let { it1 -> getEpisodeCharacters(it1) }

        }
    }


}

