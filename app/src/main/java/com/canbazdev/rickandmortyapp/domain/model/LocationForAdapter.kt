package com.canbazdev.rickandmortyapp.domain.model

/*
*   Created by hamzacanbaz on 12.07.2022
*/
data class LocationForAdapter(
    val id: Int? = null,
    val name: String? = null,
    val type: String? = null,
    val residents: List<String>? = null,
    var isDetailsOpen: Boolean? = false,
    var characters: List<Character>? = null
)