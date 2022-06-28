package com.canbazdev.rickandmortyapp.util

/*
*   Created by hamzacanbaz on 19.06.2022
*/
sealed class Event() {
    object NavigateToMain : Event()
    object NavigateToOnBoardingScreen : Event()
    data class NavigateToDetail(var characterId: Int) : Event()
    data class OpenTheLocationDetail(var locationPosition: Int) : Event()
}