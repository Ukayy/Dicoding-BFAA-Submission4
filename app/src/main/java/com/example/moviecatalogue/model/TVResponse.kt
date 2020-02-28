package com.example.moviecatalogue.model

import com.google.gson.annotations.SerializedName

data class TVResponse(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("total_results")
    val total_result: Int? = null,
    @SerializedName("total_pages")
    val total_pages: Int? = null,
    @SerializedName("results")
    val result: List<TVShow>? = null
)