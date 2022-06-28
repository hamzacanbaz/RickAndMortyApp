package com.canbazdev.rickandmortyapp.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.rickandmortyapp.adapters.characters.CharactersAdapter
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.usecase.characters.GetCharactersUseCase
import com.canbazdev.rickandmortyapp.util.Event
import com.canbazdev.rickandmortyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 20.06.2022
*/
@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel(), CharactersAdapter.OnItemClickedListener {

    private val _characters = MutableStateFlow<List<Character>>(listOf())
    val characters: StateFlow<List<Character>> = _characters

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


    init {
        getCharacters()
        goToCharacterDetail()
    }

    private fun getCharacters() {
        getCharactersUseCase().onEach { result ->
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

    // Gotodetail ile parametre gönderiliyor, burası initten aşağıdaki onclick ile mergele
    private fun goToCharacterDetail() = viewModelScope.launch {
        delay(1000)
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
}