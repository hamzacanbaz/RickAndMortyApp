package com.canbazdev.rickandmortyapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.rickandmortyapp.data.repository.DataStoreRepository
import com.canbazdev.rickandmortyapp.util.enums.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 19.06.2022
*/
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun startSplash() = viewModelScope.launch {
        delay(1000)
        dataStoreRepository.getOpenedFirstTime.collect {
            if (it) {
                eventChannel.send(Event.NavigateToMain)
            } else {
                eventChannel.send(Event.NavigateToOnBoardingScreen)
            }
        }

    }
}