package com.canbazdev.rickandmortyapp.domain.model

import com.canbazdev.rickandmortyapp.data.model.characters.Location

/*
*   Created by hamzacanbaz on 20.06.2022
*/
data class Character(
    val id: Int? = null,
    val name: String? = null,
    val species: String? = null,
    val status: String? = null,
    val location: Location? = null,
    val image: String? = null,
    val episodes: List<String>? = null,
    val gender: String? = null
)