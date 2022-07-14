package com.canbazdev.rickandmortyapp.presentation.episode_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.model.Episode
import com.canbazdev.rickandmortyapp.domain.usecase.characters.GetMultipleCharactersUseCase
import com.canbazdev.rickandmortyapp.presentation.characters.CharactersAdapter
import com.canbazdev.rickandmortyapp.util.Constants
import com.canbazdev.rickandmortyapp.util.Event
import com.canbazdev.rickandmortyapp.util.Resource
import com.canbazdev.rickandmortyapp.util.States
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 9.07.2022
*/
@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    private val getMultipleCharactersUseCase: GetMultipleCharactersUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), CharactersAdapter.OnItemClickedListener {
    private val _episode = MutableStateFlow(Episode())
    val episode: StateFlow<Episode> = _episode

    private val _uiState = MutableStateFlow(0)
    val uiState: StateFlow<Int> = _uiState

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _episodeCharacters =
        MutableStateFlow<List<Character>>(listOf())
    val episodeCharacters: StateFlow<List<Character>> = _episodeCharacters

    private val _idList = MutableStateFlow("")
    private val idList: StateFlow<String> = _idList

    private val _goToCharacterDetail = MutableStateFlow(false)
    private val goToCharacterDetail: StateFlow<Boolean> = _goToCharacterDetail

    private val _characterDetailId = MutableStateFlow(0)
    private val characterDetailId: StateFlow<Int> = _characterDetailId

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()


    init {
        goToCharacterDetail()

        savedStateHandle.get<Episode>(Constants.EPISODES_DETAIL)?.let { episode ->
            _episode.value = episode
            _isLoading.value = false
        }
        parseIdList()
        getEpisodeCharacters()
    }

    private fun parseIdList() {
        episode.value.characters?.forEach {
            _idList.value += it.substringAfter("character/") + ","
        }
        _idList.value = _idList.value.substring(0, idList.value.length - 1)

    }


    private fun getEpisodeCharacters() {
        _episodeCharacters.value = listOf()
        viewModelScope.launch {
            getMultipleCharactersUseCase.invoke(idList.value).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { characterList ->
                            _episodeCharacters.value = characterList
                            _uiState.value = States.Success.state
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = States.Error.state
                    }
                    is Resource.Loading -> {
                        _uiState.value = States.Loading.state
                    }
                }

            }

        }

    }

    override fun onItemClicked(position: Int, character: Character) {
        _characterDetailId.value = character.id ?: 0
        _goToCharacterDetail.value = true

    }

    private fun goToCharacterDetail() = viewModelScope.launch {
        goToCharacterDetail.collect {
            if (it) {
                _goToCharacterDetail.value = false
                eventChannel.send(Event.NavigateToDetail(characterDetailId.value))
            }
        }

    }



}