package com.canbazdev.rickandmortyapp.data.model.episodes


import com.google.gson.annotations.SerializedName

data class EpisodesResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Episode>
)