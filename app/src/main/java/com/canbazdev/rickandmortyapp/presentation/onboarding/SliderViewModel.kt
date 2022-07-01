package com.canbazdev.rickandmortyapp.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.data.model.OnBoardingModel
import com.canbazdev.rickandmortyapp.data.repository.DataStoreRepository
import com.canbazdev.rickandmortyapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 19.06.2022
*/
@HiltViewModel
class SliderViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {


    private val _title = MutableStateFlow("EPISODES")
    val title: StateFlow<String> = _title

    private val _desc = MutableStateFlow("")
    val desc: StateFlow<String> = _desc

    private val _image = MutableStateFlow(R.drawable.r1)
    val image: StateFlow<Int> = _image

    private val _color = MutableStateFlow(0)
    val color: StateFlow<Int> = _color

    private val _isLastScreen = MutableStateFlow(false)
    val isLastScreen: StateFlow<Boolean> = _isLastScreen

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()


    private var list: List<OnBoardingModel> = listOf(
        OnBoardingModel(
            "EPISODES", "You can access all episodes",
            R.drawable.ram1, R.color.onboarding_green
        ), OnBoardingModel(
            "CHARACTERS", "You can access all characters",
            R.drawable.ram5, R.color.onboarding_pink
        ), OnBoardingModel(
            "LOCATIONS", "You can access all locations",
            R.drawable.ram7, R.color.onboarding_orange, true
        )
    )

    init {
        viewModelScope.launch {
            getOnBoardingData(0)
        }
    }

    suspend fun getOnBoardingData(position: Int) {
        if (position < 3) {
            _title.emit(list[position].title)
            _desc.emit(list[position].desc)
            _image.emit(list[position].image)
            _color.emit(list[position].color)
            _isLastScreen.emit(list[position].isLast)
//                _isLastScreen.value = list[position].isLast

        }
    }


    fun setOpenedFirstTime() {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(Event.NavigateToMain)
            dataStoreRepository.setOpenedFirstTime(true)
        }
    }

}