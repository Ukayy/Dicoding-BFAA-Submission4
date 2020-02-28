package com.example.moviecatalogue.network
import com.example.moviecatalogue.model.MovieDetail
import com.example.moviecatalogue.model.MovieResponse
import com.example.moviecatalogue.model.TVResponse
import com.example.moviecatalogue.model.TVShowDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


// API "861f04ea1934db968c14f921833fbcc6"'

interface ApiService {
    companion object {
        private const val api_key: String ="861f04ea1934db968c14f921833fbcc6"
    }

    @GET("discover/movie?api_key=$api_key")
    fun getDiscoverMovie(): Call<MovieResponse>

    @GET("movie/{id}?api_key=$api_key")
    fun getDetailMovie(@Path("id") id: String): Call<MovieDetail>

    @GET("discover/tv?api_key=$api_key")
    fun getDiscoverTV(): Call<TVResponse>

    @GET("tv/{id}?api_key=$api_key")
    fun getDetailTV(@Path("id") id: String): Call<TVShowDetail>
}