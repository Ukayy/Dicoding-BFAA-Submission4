package com.example.moviecatalogue.model

import com.google.gson.annotations.SerializedName

data class Season(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("air_date")
    val date: String? = null,
    @SerializedName("episode_count")
    val episode: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("poster_path")
    val poster: String? = null,
    @SerializedName("season_number")
    val season: String? = null
)