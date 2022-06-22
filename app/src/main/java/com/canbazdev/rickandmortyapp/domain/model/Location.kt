package com.canbazdev.rickandmortyapp.domain.model

/*
*   Created by hamzacanbaz on 23.06.2022
*/
data class Location(
    val id: Int? = null,
    val name: String? = null,
    val type: String? = null,
    val residents: List<String>? = null
)