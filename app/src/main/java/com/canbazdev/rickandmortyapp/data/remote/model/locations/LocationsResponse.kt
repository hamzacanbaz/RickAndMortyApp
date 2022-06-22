package com.canbazdev.rickandmortyapp.data.remote.model.locations


import com.google.gson.annotations.SerializedName

data class LocationsResponse(
    @SerializedName("info")
    val info: İnfo,
    @SerializedName("results")
    val results: List<Location>
)