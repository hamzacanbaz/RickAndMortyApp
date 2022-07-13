package com.canbazdev.rickandmortyapp.data.model.locations


import com.google.gson.annotations.SerializedName

data class LocationsResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Location>
)