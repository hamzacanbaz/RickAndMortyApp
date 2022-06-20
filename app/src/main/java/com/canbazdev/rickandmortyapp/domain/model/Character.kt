package com.canbazdev.rickandmortyapp.domain.model

import com.canbazdev.rickandmortyapp.data.remote.model.characters.Location

/*
*   Created by hamzacanbaz on 20.06.2022
*/
data class Character(
    val id: Int,
    val name: String,
    val species: String,
    val status: String,
    val location: Location,
    val image: String
)