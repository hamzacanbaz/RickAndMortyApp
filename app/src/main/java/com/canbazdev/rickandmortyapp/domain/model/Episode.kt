package com.canbazdev.rickandmortyapp.domain.model

/*
*   Created by hamzacanbaz on 8.07.2022
*/
data class Episode(
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String
)