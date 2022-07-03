package com.canbazdev.rickandmortyapp.presentation.detail_character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.usecase.character_detail.GetCharacterDetailUseCase
import com.canbazdev.rickandmortyapp.util.Constants
import com.canbazdev.rickandmortyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 21.06.2022
*/
@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _character = MutableStateFlow(Character())
    val character: StateFlow<Character> = _character

    private val _uiState = MutableStateFlow(0)
    val uiState: StateFlow<Int> = _uiState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        savedStateHandle.get<String>(Constants.CHARACTER_DETAIL)?.let { characterId ->
            getCharacterDetail(characterId)
        }

    }


    private fun getCharacterDetail(characterId: String) {
        getCharacterDetailUseCase(characterId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        _character.value = it
                        _uiState.value = 1
                        _isLoading.value = false
                    }
                }
                is Resource.Error -> {
                    _uiState.value = -1
                    _isLoading.value = true
                }
                is Resource.Loading -> {
                    _uiState.value = 0
                    _isLoading.value = true
                }
            }
        }.launchIn(viewModelScope)
    }

}