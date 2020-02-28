package com.example.moviecatalogue.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.moviecatalogue.R
import com.example.moviecatalogue.adapter.TVShowAdapter
import com.example.moviecatalogue.adapter.TabAdapter
import com.example.moviecatalogue.helper.TvHelper
import com.example.moviecatalogue.model.TVShow
import com.example.moviecatalogue.ui.tvshow.TvShowDetailActivity
import com.example.moviecatalogue.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorite_main.*
import kotlinx.android.synthetic.main.fragment_favorite_t_v.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTVFragment : Fragment() {

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: TVShowAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_t_v, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showLoading(true)

        viewModel  = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FavoriteViewModel::class.java)
        adapter = TVShowAdapter(ArrayList()){}

        request()
        swipe_tv.setOnRefreshListener {
            swipe_tv.isRefreshing = false
            request()
        }
        rv_tv_favorite.layoutManager = LinearLayoutManager(activity)
    }

    private fun request() {
        viewModel.setDataTv(activity!!).observe(viewLifecycleOwner, Observer { t ->
            showLoading(false)
            t.result.let {
                if (it != null) {
                    showData(it as ArrayList<TVShow>)
                }
            }
        })
    }

    private fun showData(data: ArrayList<TVShow>) {
        adapter = TVShowAdapter(data) {
            val intent = Intent(activity, TvShowDetailActivity::class.java)
            intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, it.id)
            startActivity(intent)
        }
        adapter.notifyDataSetChanged()
        rv_tv_favorite.adapter = adapter
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            sm_tv_favorite.startShimmer()
            sm_tv_favorite.visibility = View.VISIBLE
        } else {
            sm_tv_favorite.stopShimmer()
            sm_tv_favorite.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.let { TvHelper.getInstance(it).close() }
    }
}
