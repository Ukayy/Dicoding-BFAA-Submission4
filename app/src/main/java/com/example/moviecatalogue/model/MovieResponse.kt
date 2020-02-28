package com.example.moviecatalogue.model

data class MovieResponse(
    val page: Int? = null,
    val total_result: Int? = null,
    val total_pages: Int? = null,
    val results: List<Movie>? = null
)