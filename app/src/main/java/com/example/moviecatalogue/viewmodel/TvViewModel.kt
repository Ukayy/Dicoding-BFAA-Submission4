package com.example.moviecatalogue.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.model.TVResponse
import com.example.moviecatalogue.network.NetworkConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvViewModel : ViewModel() {
    private var data = MutableLiveData<TVResponse>()

    init {
        getMovie()
    }

    private fun getMovie() {
        GlobalScope.launch(Dispatchers.Main) {
            NetworkConfig().create().getDiscoverTV().enqueue(object : Callback<TVResponse> {
                override fun onFailure(call: Call<TVResponse>, t: Throwable) {
                    Log.e("Exception", t.message)
                    data.postValue(null)
                }

                override fun onResponse(call: Call<TVResponse>, response: Response<TVResponse>) {
                    if (response.isSuccessful) {
                        response.body().let {
                            data.postValue(it)
                        }
                    }
                }

            })
        }
    }

    fun setData(): MutableLiveData<TVResponse> {
        return data
    }
}