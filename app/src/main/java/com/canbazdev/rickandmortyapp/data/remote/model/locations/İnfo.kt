package com.canbazdev.rickandmortyapp.data.remote.model.locations


import com.google.gson.annotations.SerializedName

data class İnfo(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("prev")
    val prev: Any
)