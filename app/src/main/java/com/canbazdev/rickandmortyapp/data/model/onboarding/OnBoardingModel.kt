package com.canbazdev.rickandmortyapp.data.model.onboarding

/*
*   Created by hamzacanbaz on 19.06.2022
*/
data class OnBoardingModel(
    val title: String,
    val desc: String,
    val image: Int,
    val color: Int,
    val isLast: Boolean = false
)
