package com.example.moviecatalogue.model

import com.google.gson.annotations.SerializedName

data class TVShowDetail(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("backdrop_path")
    val backdrop: String? = null,
    @SerializedName("first_air_date")
    val date: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("poster_path")
    val poster: String? = null,
    @SerializedName("vote_average")
    val score: String? = null,
    @SerializedName("seasons")
    val season: List<Season>? = null
)