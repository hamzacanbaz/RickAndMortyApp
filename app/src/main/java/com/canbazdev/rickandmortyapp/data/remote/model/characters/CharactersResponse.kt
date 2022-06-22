package com.canbazdev.rickandmortyapp.data.remote.model.characters


import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<CharacterDetail>
)