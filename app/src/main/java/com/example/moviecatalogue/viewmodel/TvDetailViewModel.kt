package com.example.moviecatalogue.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.db.DatabaseContract
import com.example.moviecatalogue.helper.MappingHelper
import com.example.moviecatalogue.helper.TvHelper
import com.example.moviecatalogue.model.TVShowDetail
import com.example.moviecatalogue.network.NetworkConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvDetailViewModel : ViewModel() {
    private var data = MutableLiveData<TVShowDetail>()
    private var isLike = MutableLiveData<Boolean>()
    private lateinit var tvHelper: TvHelper

    private fun getData(id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            isLike.postValue(false)
            tvHelper.open()
            val cursor = tvHelper.getById(id)
            Log.e("HH", "Cursor: ${cursor.count}")
            if (cursor.count > 0) {
                isLike.postValue(true)
                val tvShow = MappingHelper.toTv(cursor)
                data.postValue(
                    TVShowDetail(
                        id = tvShow.id,
                        name = tvShow.title,
                        overview = tvShow.overview,
                        score = tvShow.score,
                        date = tvShow.date,
                        poster = tvShow.poster,
                        backdrop = ""
                    )
                )
            } else {
                fromServer(id)
            }
        }
    }

    private fun fromServer(id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            NetworkConfig().create().getDetailTV(id).enqueue(object : Callback<TVShowDetail> {
                override fun onFailure(call: Call<TVShowDetail>, t: Throwable?) {
                    Log.e("Exception", t?.message)
                    data.postValue(null)
                }

                override fun onResponse(
                    call: Call<TVShowDetail>,
                    response: Response<TVShowDetail>
                ) {
                    if (response.isSuccessful) {
                        response.body().let {
                            data.postValue(it)
                        }
                    }
                }

            })
        }
    }

    fun setData(context: Context, id: String?): MutableLiveData<TVShowDetail> {
        tvHelper = TvHelper.getInstance(context)

        if (id != null) {
            getData(id)
        }
        return data
    }

    fun setDataFromServer(context: Context, id: String): MutableLiveData<TVShowDetail> {
        tvHelper = TvHelper.getInstance(context)
        fromServer(id)

        return data
    }

    fun addFavorite(tv: TVShowDetail) {
        GlobalScope.launch(Dispatchers.Main) {
            tvHelper.open()
            if (isLike.value == true) {
//            unFavorite TV
                val result = tvHelper.deleteById(tv.id.toString())
                if (result > 0) {
                    isLike.postValue(false)
                }
            } else {
                val values = ContentValues()
                values.put(DatabaseContract.TvColumns.TITLE, tv.name)
                values.put(DatabaseContract.TvColumns.TV_ID, tv.id)
                values.put(DatabaseContract.TvColumns.POSTER, tv.poster)
                values.put(DatabaseContract.TvColumns.SCORE, tv.score)
                values.put(DatabaseContract.TvColumns.DATE, tv.date)
                values.put(DatabaseContract.TvColumns.OVERVIEW, tv.overview)
                val result = tvHelper.insert(values)
                if (result > 0) {
                    isLike.postValue(true)
                }
            }
        }
    }

    fun stateIsFavorite(): MutableLiveData<Boolean> {
        return isLike
    }
}