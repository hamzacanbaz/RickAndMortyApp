package com.canbazdev.rickandmortyapp.data.model.characters


import com.canbazdev.rickandmortyapp.domain.model.Character
import com.google.gson.annotations.SerializedName

data class CharacterDetail(
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val origin: Origin,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)

fun CharacterDetail.toCharacter(): Character {
    return Character(
        id, name, species, status, location, image, episode, gender
    )
}