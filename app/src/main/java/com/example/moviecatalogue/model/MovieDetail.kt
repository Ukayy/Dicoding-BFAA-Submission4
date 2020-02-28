package com.example.moviecatalogue.model

import com.google.gson.annotations.SerializedName


data class MovieDetail(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("original_title")
    val title: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("release_date")
    val date: String? = null,
    @SerializedName("tagline")
    val tagLine: String? = null,
    @SerializedName("poster_path")
    val poster: String? = null,
    @SerializedName("backdrop_path")
    val backdrop: String? = null,
    @SerializedName("vote_average")
    val score: String? = null
)
