package com.canbazdev.rickandmortyapp.data.model.locations


import com.canbazdev.rickandmortyapp.domain.model.Location
import com.google.gson.annotations.SerializedName


data class Location(
    @SerializedName("created")
    val created: String,
    @SerializedName("dimension")
    val dimension: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("residents")
    val residents: List<String>,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)

fun com.canbazdev.rickandmortyapp.data.model.locations.Location.toLocation(): Location {
    return Location(
        id, name, type, residents
    )
}