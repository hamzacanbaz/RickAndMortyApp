package com.canbazdev.rickandmortyapp.data.remote.model.episodes


import com.canbazdev.rickandmortyapp.domain.model.Episode
import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("characters")
    val characters: List<String>,
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

fun com.canbazdev.rickandmortyapp.data.remote.model.episodes.Episode.toEpisode(): Episode {
    return Episode(airDate, characters, created, episode, id, name)
}