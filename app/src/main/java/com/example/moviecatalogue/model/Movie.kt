package com.example.moviecatalogue.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Movie(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("original_title")
    var title: String? = null,
    @SerializedName("vote_average")
    var score: String? = null,
    @SerializedName("release_date")
    var date: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("poster_path")
    var poster: String? = null
) : Parcelable