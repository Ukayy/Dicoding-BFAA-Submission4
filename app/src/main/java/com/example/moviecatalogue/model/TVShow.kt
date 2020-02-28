package com.example.moviecatalogue.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TVShow(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var title: String? = null,
    @SerializedName("vote_average")
    var score: String? = null,
    @SerializedName("first_air_date")
    var date: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("poster_path")
    var poster: String? = null
):Parcelable