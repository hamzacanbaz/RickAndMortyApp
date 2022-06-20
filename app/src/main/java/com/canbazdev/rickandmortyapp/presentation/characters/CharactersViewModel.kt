package com.canbazdev.rickandmortyapp.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.rickandmortyapp.adapters.characters.CharactersAdapter
import com.canbazdev.rickandmortyapp.domain.model.Character
import com.canbazdev.rickandmortyapp.domain.usecase.characters.GetCharactersUseCase
import com.canbazdev.rickandmortyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 20.06.2022
*/
@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel(), CharactersAdapter.OnItemClickedListener {

    private val _characters = MutableStateFlow<List<Character>>(listOf())
    val characters: MutableStateFlow<List<Character>> = _characters

    private val _error = MutableStateFlow("")
    val error: MutableStateFlow<String> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _uiState = MutableStateFlow(0)
    val uiState: MutableStateFlow<Int> = _uiState

    init {
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

    override fun onItemClicked(position: Int, character: Character) {
        println(position)
    }
}