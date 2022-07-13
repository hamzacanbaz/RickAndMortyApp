package com.canbazdev.rickandmortyapp.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.canbazdev.rickandmortyapp.data.repository.DataStoreRepository
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.repository.RickAndMortyRepository
import com.canbazdev.rickandmortyapp.domain.usecase.characters.GetCharactersUseCase
import com.canbazdev.rickandmortyapp.domain.usecase.characters.GetFilterCharactersUseCase
import com.canbazdev.rickandmortyapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 20.06.2022
*/
@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getFilterCharactersUseCase: GetFilterCharactersUseCase,
    private val dataStoreRepository: DataStoreRepository,
    private val rickAndMortyRepository: RickAndMortyRepository
) : ViewModel(), CharactersAdapter.OnItemClickedListener {

    private val _characters = MutableStateFlow<PagingData<Character>>(PagingData.empty())
    val characters: StateFlow<PagingData<Character>> = _characters

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _uiState = MutableStateFlow(0)
    val uiState: StateFlow<Int> = _uiState

    private val _goToCharacterDetail = MutableStateFlow(false)
    private val goToCharacterDetail: StateFlow<Boolean> = _goToCharacterDetail

    private val _characterDetailId = MutableStateFlow(0)
    private val characterDetailId: StateFlow<Int> = _characterDetailId

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val _currentLayoutManager = MutableStateFlow(LayoutManagers.LINEAR_LAYOUT_MANAGER)
    val currentLayoutManager: StateFlow<LayoutManagers> = _currentLayoutManager

    private val _filterName = MutableStateFlow<String?>(null)
    private val filterName: StateFlow<String?> = _filterName

    private val _filterStatus = MutableStateFlow<Status?>(null)
    private val filterStatus: StateFlow<Status?> = _filterStatus

    private val _filterGender = MutableStateFlow<Gender?>(null)
    private val filterGender: StateFlow<Gender?> = _filterGender

    private val _filterMap = MutableStateFlow<Map<String, String>?>(null)
    private val filterMap: StateFlow<Map<String, String>?> = _filterMap


    init {
        viewModelScope.launch {
            dataStoreRepository.getCharactersLayoutManager.collect {
                _currentLayoutManager.value = LayoutManagers.values()[it]
            }
        }

        getCharacters()
        goToCharacterDetail()
    }

    fun updateFilterStatus(status: Status) {
        _filterStatus.value = status
    }

    fun updateFilterGender(gender: Gender) {
        _filterGender.value = gender
    }

    fun updateName(name: String) {
        _filterName.value = name.trim()
    }

    private fun getCharacters() {
//        rickAndMortyRepository.getCharacters().collect {
//            _characters.value = it.map { d->
//                println(d.toCharacter())
//                d.toCharacter()
//            }
//
//        }
        viewModelScope.launch {
            getCharactersUseCase.invoke(coroutineScope = viewModelScope).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { list ->
                            _characters.value = list
                            _uiState.value = 1
                        }
                    }
                    is Resource.Error -> {
                        _error.value = result.errorMessage ?: "Unexpected error!"
                        _uiState.value = -1
                    }
                    is Resource.Loading -> {
                        _isLoading.value = true
                        _uiState.value = 0
                    }
                }
            }

        }
    }

    private fun setFilterMap() {
        val data: MutableMap<String, String> = HashMap()
        if (filterName.value != null) {
            data["name"] = filterName.value!!
        }
        if (filterStatus.value != null) {
            data["status"] = filterStatus.value!!.name
        }
        if (filterGender.value != null) {
            data["gender"] = filterGender.value!!.name
        }
        _filterMap.value = data

    }

    fun getFilterCharacters() {
        setFilterMap()
        filterMap.value?.let {
            getFilterCharactersUseCase(
                it,
                viewModelScope
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { list ->
                            _characters.value = list
                            _uiState.value = 1
                        }
                    }
                    is Resource.Error -> {
                        _error.value = result.errorMessage ?: "Unexpected error!"
                        _uiState.value = -1
                    }
                    is Resource.Loading -> {
                        _isLoading.value = true
                        _uiState.value = 0
                    }
                }
            }.launchIn(viewModelScope)
        }

    }

    private fun goToCharacterDetail() = viewModelScope.launch {
        goToCharacterDetail.collect {
            if (it) {
                _goToCharacterDetail.value = false
                eventChannel.send(Event.NavigateToDetail(characterDetailId.value))
            }
        }

    }

    override fun onItemClicked(position: Int, character: Character) {
        _characterDetailId.value = character.id ?: 0
        _goToCharacterDetail.value = true
    }

    fun changeCurrentLayoutManager(layoutManagers: LayoutManagers) = viewModelScope.launch {
        // TODO edit for data store
        dataStoreRepository.setCharactersLayoutManager(layoutManagers)
        _currentLayoutManager.value = layoutManagers
    }

    fun clearFilterAreas() {
        _filterGender.value = null
        _filterStatus.value = null
        _filterName.value = null
    }

}