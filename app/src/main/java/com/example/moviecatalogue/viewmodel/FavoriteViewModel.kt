package com.example.moviecatalogue.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.helper.MappingHelper
import com.example.moviecatalogue.helper.MovieHelper
import com.example.moviecatalogue.helper.TvHelper
import com.example.moviecatalogue.model.MovieResponse
import com.example.moviecatalogue.model.TVResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class FavoriteViewModel : ViewModel() {
    private var data = MutableLiveData<MovieResponse>()
    private var dataTv = MutableLiveData<TVResponse>()
    private lateinit var movieHelper: MovieHelper
    private lateinit var tvHelper: TvHelper

    private fun getMovie(context: Context) {
        movieHelper = MovieHelper.getInstance(context)
        GlobalScope.launch(Dispatchers.Main) {
            val deferredMovie = async(Dispatchers.IO) {
                val cursor = movieHelper.getAll()
                MappingHelper.toListMovie(cursor)
            }

            val movies = deferredMovie.await()
            if (movies.isNotEmpty()) {
                val movieResponse = MovieResponse(1, movies.size, 1, movies)
                data.postValue(movieResponse)
            } else {
                val movieResponse = MovieResponse(1, movies.size, 1, ArrayList())
                data.postValue(movieResponse)
            }
        }
    }

    private fun getTvShow(context: Context) {
        tvHelper = TvHelper.getInstance(context)
        GlobalScope.launch(Dispatchers.Main) {
            val deferredTv = async(Dispatchers.IO) {
                val cursor = tvHelper.getAll()
                MappingHelper.toListTv(cursor)
            }

            val tvShow = deferredTv.await()
            if (tvShow.isNotEmpty()) {
                val tvResponse = TVResponse(1, tvShow.size, 1, tvShow)
                dataTv.postValue(tvResponse)
            } else {
                val tvResponse = TVResponse(1, tvShow.size, 1, ArrayList())
                dataTv.postValue(tvResponse)
            }
        }
    }

    fun setDataMovie(context: Context): MutableLiveData<MovieResponse> {
        getMovie(context)
        return data
    }

    fun setDataTv(context: Context): MutableLiveData<TVResponse> {
        getTvShow(context)
        return dataTv
    }
}