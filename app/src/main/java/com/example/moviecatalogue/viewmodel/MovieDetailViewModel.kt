package com.example.moviecatalogue.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.db.DatabaseContract
import com.example.moviecatalogue.helper.MappingHelper
import com.example.moviecatalogue.helper.MovieHelper
import com.example.moviecatalogue.model.MovieDetail
import com.example.moviecatalogue.network.NetworkConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieDetailViewModel : ViewModel() {
    private var data = MutableLiveData<MovieDetail>()
    private var isLike = MutableLiveData<Boolean>()
    private lateinit var movieHelper: MovieHelper

    private fun getData(id: String) {
        GlobalScope.launch(Dispatchers.Main){
            movieHelper.open()
            val cursor = movieHelper.getById(id)
            Log.e("HH", "Cursor: ${cursor.count}")
            if (cursor.count > 0) {
                isLike.value = true
                val movie = MappingHelper.toMovie(cursor)
                data.postValue(MovieDetail(
                    id = movie.id,
                    title = movie.title,
                    overview = movie.overview,
                    score = movie.score,
                    date = movie.date,
                    poster = movie.poster,
                    backdrop = "",
                    tagLine = ""
                ))
            } else {
                isLike.postValue(false)
                fromServer(id)
            }
        }
    }

    private fun fromServer(id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            NetworkConfig().create().getDetailMovie(id).enqueue(object : Callback<MovieDetail> {
                override fun onFailure(call: Call<MovieDetail>, t: Throwable?) {
                    Log.e("Exception", t?.message)
                    data.postValue(null)
                }

                override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                    if (response.isSuccessful) {
                        response.body().let {
                            data.postValue(it)
                        }
                    }
                }

            })
        }
    }

    fun setData(context: Context, id: String?): MutableLiveData<MovieDetail> {
        movieHelper = MovieHelper.getInstance(context)

        if (id != null) {
            getData(id)
        }
        return data
    }

    fun setDataFromServer(context: Context, id: String):MutableLiveData<MovieDetail>{
        fromServer(id)
        return data
    }

    fun addFavorite(movie: MovieDetail) {
        GlobalScope.launch(Dispatchers.Main) {
            movieHelper.open()
            if (isLike.value == true) {
//            unFavorite movie
                val result = movieHelper.deleteById(movie.id.toString())
                if (result >= 0) {
                    isLike.postValue(false)
                }
            } else {
                async(Dispatchers.IO) {
                    val values = ContentValues()
                    values.put(DatabaseContract.MovieColumns.TITLE, movie.title)
                    values.put(DatabaseContract.MovieColumns.MOVIE_ID, movie.id)
                    values.put(DatabaseContract.MovieColumns.POSTER, movie.poster)
                    values.put(DatabaseContract.MovieColumns.SCORE, movie.score)
                    values.put(DatabaseContract.MovieColumns.DATE, movie.date)
                    values.put(DatabaseContract.MovieColumns.OVERVIEW, movie.overview)
                    val result = movieHelper.insert(values)
                    if (result >= 0) {
                        isLike.postValue(true)
                    }
                }
            }
        }
    }

    fun stateIsFavorite(): MutableLiveData<Boolean> {
        return isLike
    }
}